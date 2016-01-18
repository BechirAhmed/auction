package com.auction.auction;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auction.auction.data.models.AddProductRequestModel;
import com.auction.auction.data.services.IProductRemoteService;
import com.auction.auction.data.services.ProductRemoteService;
import com.auction.auction.utils.ImageUploadUtils;

import org.json.JSONObject;

import java.net.ProtocolException;

import br.com.goncalves.pugnotification.notification.PugNotification;

public class AddProductActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PICK_IMAGE = 1001;

    private AddProductTask mAddProductTask = null;
    private EditText mNameView;
    private EditText mStartingPriceView;
    private EditText mFinalPriceView;
    private EditText mDescriptionView;
    private String mAuthToken;
    private String mCategoryId;
    private String picturePath = "";
    private String uploadedImageUrl = "";
    public static boolean imageUploadFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        if (savedInstanceState == null) {
            Bundle intentExtras = getIntent().getExtras();
            if (intentExtras == null) {
                mAuthToken = null;
                mCategoryId = null;
            } else {
                mAuthToken = intentExtras.getString("authToken");
                mCategoryId = intentExtras.getString("categoryId");
            }
        } else {
            mAuthToken = (String) savedInstanceState.getSerializable("authToken");
            mAuthToken = (String) savedInstanceState.getSerializable("categoryId");
        }

        mNameView = (EditText) findViewById(R.id.product_name_input);
        mDescriptionView = (EditText) findViewById(R.id.product_description);
        mStartingPriceView = (EditText) findViewById(R.id.product_starting_price);
        mFinalPriceView = (EditText) findViewById(R.id.product_final_price);

        Button selectImageButton = (Button) findViewById(R.id.select_image);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        Button addProductButton = (Button) findViewById(R.id.action_add_product);
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
        String startingPrice = mStartingPriceView.getText().toString();
        String finalPrice = mFinalPriceView.getText().toString();
        String description = mDescriptionView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(startingPrice)) {
            mStartingPriceView.setError(getString(R.string.error_field_required));
            focusView = mStartingPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(finalPrice)) {
            mFinalPriceView.setError(getString(R.string.error_field_required));
            focusView = mFinalPriceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }

        if (Double.parseDouble(startingPrice) >= Double.parseDouble(finalPrice)) {
            mStartingPriceView.setError(getString(R.string.starting_price_lower_than_final));
            focusView = mDescriptionView;
            cancel = true;
        }

        if (Double.parseDouble(finalPrice) == 0D) {
            mFinalPriceView.setError(getString(R.string.product_final_price_zero));
            focusView = mFinalPriceView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (picturePath != null && picturePath.length() > 0) {
                (new UploadToImgurTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, picturePath);
            }

            mAddProductTask = new AddProductTask(
                    new AddProductRequestModel(
                            name,
                            Double.parseDouble(startingPrice),
                            Double.parseDouble(finalPrice), uploadedImageUrl, description),
                    mAuthToken,
                    mCategoryId);
            mAddProductTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void)null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            Log.d("AddProductActivity" , "Image data is null.");
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public class AddProductTask extends AsyncTask<Void, Void, Boolean> {
        private final AddProductRequestModel mModel;
        private String mAuthToken;
        private String mCategoryId;

        AddProductTask(AddProductRequestModel model, String authToken, String categoryId) {
            mModel = model;
            mAuthToken = authToken;
            mCategoryId = categoryId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Waits for the upload to finish, and then adds the products.
            while (!imageUploadFinished) {}

            mModel.imageUrl = uploadedImageUrl;
            IProductRemoteService products = new ProductRemoteService(mAuthToken);
            if (products.addProduct(mModel, mCategoryId)) {
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean added) {
            mAddProductTask = null;
            if (added) {
                PugNotification.with(getApplicationContext())
                        .load()
                        .smallIcon(R.mipmap.ic_launcher)
                        .autoCancel(true)
                        .largeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .title(R.string.pug_notification_title)
                        .message(R.string.pug_notification_message)
                        .simple()
                        .build();

                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.product_could_not_be_added, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAddProductTask = null;
        }
    }

    public class UploadToImgurTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            ImageUploadUtils imageUpload = new ImageUploadUtils();
            try {
                String result = imageUpload.Upload(params[0]);
                JSONObject json = new JSONObject(result);
                JSONObject data = json.optJSONObject("data");
                uploadedImageUrl = data.optString("link");
            } catch (ProtocolException e) {
                Toast.makeText(getApplicationContext(), R.string.image_upload_service_down, Toast.LENGTH_SHORT).show();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result.booleanValue()) {
                imageUploadFinished = true;
            }
        }
    }
}