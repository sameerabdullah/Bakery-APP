package com.sameer.bitekitchenette.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.bitekitchenette.activities.CartJActivity;
import com.sameer.bitekitchenette.activities.HomeJActivity;
import com.sameer.bitekitchenette.viewHolders.PackSizeListViewJHolder;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.models.PackSizeJ;
import com.sameer.bitekitchenette.utills.Utills;

import java.util.List;

public class PackSizeListJAdapter extends RecyclerView.Adapter<PackSizeListViewJHolder> {

    public Activity activity;
    public List<PackSizeJ> sizesList;
    public Boolean showButtons;
    public Boolean deal;

    public PackSizeListJAdapter(Activity activity, List<PackSizeJ> sizesList, Boolean showButtons, Boolean deal){
        this.activity = activity;
        this.sizesList = sizesList;
        this.showButtons = showButtons;
        this.deal = deal;
    }

    @NonNull
    @Override
    public PackSizeListViewJHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackSizeListViewJHolder(LayoutInflater.from(activity).inflate(R.layout.single_pack_size_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackSizeListViewJHolder holder, int position) {
        PackSizeJ item = sizesList.get(position);

            if (deal)
                holder.price.setText(item.packSize + "-PCS");
            else
                holder.size.setText(item.packSize + "-PCS");

        if(item.price != -1)
            if(!deal)
                holder.price.setText("Rs. " + item.price);

        if(item.quantity > 0) {
            if(showButtons)
                holder.quantity.setText(String.valueOf(item.quantity));
            else {
                if(!deal)
                    holder.price.setText("Rs. " + item.price + " x " + item.quantity);
            }

            holder.quantity.setVisibility(View.VISIBLE);
            holder.increaseQuantity.setVisibility(View.VISIBLE);
            holder.decreaseQuantity.setVisibility(View.VISIBLE);
            holder.cart.setImageResource(R.drawable.ic_delete_grey);
        } else {
            holder.quantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.cart.setVisibility(View.VISIBLE);
        }

        if(item.free != -1)
            holder.free.setText("(" + item.free + " free)");
        else
            holder.free.setVisibility(View.GONE);

        if(deal) {
            holder.size.setText(item.name);
            holder.quantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.cart.setVisibility(View.GONE);
            holder.free.setVisibility(View.GONE);
        } else {

        }

        if(!showButtons){
            holder.quantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.cart.setVisibility(View.GONE);
        } else {

        }

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity) {
                    ((CartJActivity) activity).deleteItemFromCart(item, deal);
                    ((CartJActivity) activity).setCartCount();
                } else {
                    Utills.addToCart(activity, item, deal);
                }
                if(activity instanceof HomeJActivity){
                    ((HomeJActivity) activity).setCartCount();
                }
            }
        });

        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity){
                    ((CartJActivity) activity).addToCart(item, deal);
                    ((CartJActivity) activity).setCartCount();
                }
            }
        });

        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity){
                    ((CartJActivity) activity).decreaseQuantity(item, deal);
                    ((CartJActivity) activity).setCartCount();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizesList.size();
    }
}
