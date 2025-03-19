package com.rogger.myapplication.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rogger.myapplication.R;
import com.rogger.myapplication.database.ProductEntity;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ProductViewHolder> {
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
       // private final TextView productName;
       // private final TextView productQuantity;

        ProductViewHolder(View itemView) {
            super(itemView);
           // productName = itemView.findViewById(R.id.textViewProductName);
           // productQuantity = itemView.findViewById(R.id.textViewProductQuantity);
        }

        void bind(ProductEntity product) {
           // productName.setText(product.name);
            //productQuantity.setText("Quantidade: " + product.quantity);
        }
    }
}
