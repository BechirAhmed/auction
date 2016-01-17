package com.auction.auction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auction.auction.data.models.AddProductRequestModel;
import com.auction.auction.data.models.AddProductResponseModel;
import com.auction.auction.utils.GetRequestUtils;
import com.auction.auction.utils.PostRequestUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private static final String ADD_PRODUCT_URL = "http://android-auction.herokuapp.com/api/categories/5699ccb82307b58816f64b2a/products";

    private AddProductTask mAddProductTask = null;
    private EditText mNameView;
    private EditText mPriceView;
    private EditText mRealPriceView;
    private EditText mImgURLView;
    private EditText mDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mNameView = (EditText) findViewById(R.id.inputProductName);
        mPriceView = (EditText) findViewById(R.id.inputProductPrice);
        mRealPriceView = (EditText) findViewById(R.id.inputRealPrice);
        mImgURLView = (EditText) findViewById(R.id.inputImageURL);
        mDescriptionView = (EditText) findViewById(R.id.inputDescription);

        Button addProductButton = (Button) findViewById(R.id.productAddProduct);
        addProductButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });


    }

    private void addProduct() {
        if (mAddProductTask != null) {
            return;
        }

        String name = mNameView.getText().toString();
        String price = mPriceView.getText().toString();
        String realPrice = mRealPriceView.getText().toString();
        String imgUrl = mImgURLView.getText().toString();
        String description = mDescriptionView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(price)) {
            mPriceView.setError(getString(R.string.error_field_required));
            focusView = mPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(realPrice)) {
            mRealPriceView.setError(getString(R.string.error_field_required));
            focusView = mRealPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(imgUrl)) {
            mImgURLView.setError(getString(R.string.error_field_required));
            focusView = mImgURLView;
            cancel = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAddProductTask = new AddProductTask(new AddProductRequestModel(name, price, realPrice, imgUrl, description));
            mAddProductTask.execute((Void) null);
        }
    }
    public class AddProductTask extends AsyncTask<Void, Void, Boolean> {
        private final AddProductRequestModel mModel;

        AddProductTask(AddProductRequestModel model) {
            mModel = model;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String plainToken = String.format("%s:%s", mModel.name, mModel.price, mModel.realPrice, mModel.imageUrl, mModel.description);
            String encodedToken = Base64.encodeToString(plainToken.getBytes(), Base64.DEFAULT);

            if (isProductAddedSuccessfully(mModel, encodedToken)) {
                return true;
            }

            return false;
        }

        private boolean isProductAddedSuccessfully(AddProductRequestModel model, String encodedToken) {
            Map<String, String> requestHeaders = new HashMap<String, String>();
            requestHeaders.put("Authorization", "Basic " + encodedToken);
            String addProductResul = GetRequestUtils.make(AddProductActivity.ADD_PRODUCT_URL, requestHeaders);
            Log.d("", addProductResul);
            if (addProductResul.equals("Authorized")) {
                return true;
            }

            return false;
        }

    }
}