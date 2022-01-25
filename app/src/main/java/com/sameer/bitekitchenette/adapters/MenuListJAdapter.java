package com.sameer.bitekitchenette.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.bitekitchenette.activities.CartJActivity;
import com.sameer.bitekitchenette.models.MenuItemJ;
import com.sameer.bitekitchenette.viewHolders.MenuListViewJHolder;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.utills.Utills;

import java.util.List;

public class MenuListJAdapter extends RecyclerView.Adapter<MenuListViewJHolder>{

    public Activity activity;
    public List<MenuItemJ> sizesList;
    public Boolean showBorder;

    public MenuListJAdapter(Activity activity, List<MenuItemJ> sizesList, Boolean showBorder){
        this.activity = activity;
        this.sizesList = sizesList;
        this.showBorder = showBorder;
    }

    @NonNull
    @Override
    public MenuListViewJHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuListViewJHolder(LayoutInflater.from(activity).inflate(R.layout.single_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListViewJHolder holder, int position) {
        MenuItemJ item = sizesList.get(position);

        if(item.itemName != null)
            holder.itemName.setText(item.itemName);

        if(item.deal){
            if(showBorder)
                holder.price.setText("Rs. " + item.packSizes.get(0).getPrice());
            else
            holder.price.setText("Rs. " + item.packSizes.get(0).getPrice() + " x " + item.packSizes.get(0).getQuantity());


            if(item.packSizes.get(0).getQuantity() > 0){
                holder.quantity.setText(String.valueOf(item.packSizes.get(0).getQuantity()));
                holder.cart.setVisibility(View.VISIBLE);
                holder.price.setVisibility(View.VISIBLE);
                holder.quantity.setVisibility(View.VISIBLE);
                holder.increaseQuantity.setVisibility(View.VISIBLE);
                holder.decreaseQuantity.setVisibility(View.VISIBLE);
                holder.cart.setImageResource(R.drawable.ic_delete_grey);
            } else {
                holder.cart.setVisibility(View.VISIBLE);
                holder.price.setVisibility(View.VISIBLE);
            }
        } else {
            holder.price.setVisibility(View.GONE);
            holder.quantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.cart.setVisibility(View.GONE);
        }

        if(item.packSizes != null && item.packSizes.size() > 0){
            RecyclerView recyclerView = holder.packSizeList;
            recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
            PackSizeListJAdapter adapter = new PackSizeListJAdapter(activity, item.packSizes, showBorder, item.deal);
            recyclerView.setAdapter(adapter);
            recyclerView.invalidate();
        }

        if(!showBorder){
            holder.menuCard.setCardElevation(0f);
            holder.quantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.cart.setVisibility(View.GONE);
        }



        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity) {
                    ((CartJActivity) activity).deleteItemFromCart(item.packSizes.get(0), item.deal);
                    ((CartJActivity) activity).setCartCount();
                } else {
                    Utills.addToCart(activity, item.packSizes.get(0), item.deal);
                }
//                if(activity instanceof HomeActivity){
//                    ((CartJActivity)  activity).setCartCount();
//                }
            }
        });

        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity){
                    ((CartJActivity) activity).addToCart(item.packSizes.get(0), item.deal);
                    ((CartJActivity) activity).setCartCount();
                }
            }
        });

        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof CartJActivity){
                    ((CartJActivity) activity).decreaseQuantity(item.packSizes.get(0), item.deal);
                    ((CartJActivity) activity).setCartCount();
                }
            }
        });{

        }
    }

    @Override
    public int getItemCount() {
        return sizesList.size();
    }
}
