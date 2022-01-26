package com.sameer.bitekitchenette.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.bitekitchenette.R;

public class MenuListViewJHolder extends RecyclerView.ViewHolder {

    public CardView menuCard;
    public ConstraintLayout menuItem;
    public TextView itemName, price, quantity;
    public ImageView increaseQuantity, decreaseQuantity, cart;
    public RecyclerView packSizeList;
    public Button addToCart;

    public MenuListViewJHolder(View itemView){
        super(itemView);
        menuCard = itemView.findViewById(R.id.menu_card);
        menuItem = itemView.findViewById(R.id.menu_item);
        itemName = itemView.findViewById(R.id.item_name);
        packSizeList = itemView.findViewById(R.id.pack_sizes);
        addToCart = itemView.findViewById(R.id.add_to_cart);
        price = itemView.findViewById(R.id.price);
        quantity = itemView.findViewById(R.id.quantity);
        increaseQuantity = itemView.findViewById(R.id.increase_quantity);
        decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);
        cart = itemView.findViewById(R.id.cart);
    }
}
