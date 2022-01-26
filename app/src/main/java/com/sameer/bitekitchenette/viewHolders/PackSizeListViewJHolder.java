package com.sameer.bitekitchenette.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.bitekitchenette.R;

public class PackSizeListViewJHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout sizeItem;
    public TextView size, free, price, quantity;
    public ImageView increaseQuantity, decreaseQuantity, cart;

    public PackSizeListViewJHolder(@NonNull View itemView) {
        super(itemView);

        sizeItem = itemView.findViewById(R.id.pack_size_item);
        size = itemView.findViewById(R.id.size);
        free  = itemView.findViewById(R.id.free);
        price = itemView.findViewById(R.id.price);
        quantity = itemView.findViewById(R.id.quantity);
        increaseQuantity = itemView.findViewById(R.id.increase_quantity);
        decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);
        cart = itemView.findViewById(R.id.cart);
    }
}
