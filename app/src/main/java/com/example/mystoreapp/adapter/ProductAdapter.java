package com.example.mystoreapp.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mystoreapp.MainActivity;
import com.example.mystoreapp.R;
import com.example.mystoreapp.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    // Creating orderCallback interface
    private OrderCallback orderCallback;

    // Constructor for ProductAdapter
    public ProductAdapter(Context context, List<Product> objects) {
        super(context, R.layout.item_belanja, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Create listItem View
        View listItem = convertView;
        if (listItem == null)
            // Inflate listItem with item_belanja layout
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_belanja, parent, false);
        // Getting item data for product
        final Product product = getItem(position);
        // Initialize Views
        ImageView imgProduct = listItem.findViewById(R.id.img_produk);
        TextView tvProductName = listItem.findViewById(R.id.tv_produk);
        TextView tvProductPrice = listItem.findViewById(R.id.tv_harga);
        Button btnOrder = listItem.findViewById(R.id.btn_pesan);
        // If product is not empty
        if (product != null) {
            // Set values for Views
            Glide.with(getContext()).load(product.getProductImg()).into(imgProduct);
            tvProductName.setText(product.getProductName());
            tvProductPrice.setText("Rp. " + String.valueOf(product.getProductPrice()));
            // If this adapter is used for MainActivity
            if (getContext() instanceof MainActivity){
                // hide btnOrder
                btnOrder.setVisibility(View.GONE);
            }
            // Set onlickListener for btnOrder
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderCallback != null) {
                        // Giving the callback
                        orderCallback.orderOnclick(
                                product.getId(),
                                product.getProductName(),
                                product.getProductPrice(),
                                product.getProductImg()
                        );
                    }
                }
            });
        }
        return listItem;
    }

    //Make callback
    public void orderOnClick(OrderCallback callback) {
        this.orderCallback = callback;
    }

    public interface OrderCallback {
        void orderOnclick(int id, String productName, int productPrice, String productImage);
    }
}
