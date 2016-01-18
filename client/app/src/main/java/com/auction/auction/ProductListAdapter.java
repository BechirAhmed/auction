package com.auction.auction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.auction.auction.data.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private List<Product> mProductList;

    public ProductListAdapter(Context context, List<Product> products) {
        super(context, R.layout.list_item, products);
        this.mProductList = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.list_item, parent, false);

        Product product = getItem(position);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
        Picasso
                .with(getContext())
                .load(product.imageUrl)
                .fit()
                .into(imageView);

        TextView productName = (TextView) convertView.findViewById(R.id.title);
        productName.setText(product.name);
        TextView productDescription = (TextView)convertView.findViewById(R.id.description);
        productDescription.setText(product.description);
        TextView productCurrentPrice = (TextView)convertView.findViewById(R.id.price);
        productCurrentPrice.setText(String.format("%1$,.2f BGN", product.currentPrice));
        return convertView;
    }
}