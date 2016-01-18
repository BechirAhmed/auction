package com.auction.auction;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.auction.auction.data.models.Product;
import com.auction.auction.data.services.IProductRemoteService;
import com.auction.auction.data.services.ProductRemoteService;

import java.util.List;

public class ProductsListFragment extends ListFragment implements OnItemClickListener {
    private String mCategoryId;
    private String mAuthToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_list_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            mCategoryId = args.getString("categoryId", null);
            mAuthToken = args.getString("authToken", null);
        }

        if (mCategoryId != null && mAuthToken != null) {
            (new GetProductsTask()).execute(mAuthToken, mCategoryId);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Product: " + position, Toast.LENGTH_SHORT).show();
    }

    private class GetProductsTask extends AsyncTask<String, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(String... params) {
            String encodedToken = params[0];
            String categoryId = params[1];

            IProductRemoteService products = new ProductRemoteService(encodedToken);
            return products.getProductsByCategoryId(categoryId);
        }

        @Override
        protected void onPostExecute(final List<Product> productsList) {
            if (productsList == null || productsList.size() == 0) {
                Toast.makeText(getActivity(), R.string.no_products_for_category, Toast.LENGTH_SHORT).show();
            }

            ProductListAdapter adapter = new ProductListAdapter(getActivity(), productsList);
            getListView().setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(), R.string.could_not_fetch_products, Toast.LENGTH_LONG).show();
        }
    }
}