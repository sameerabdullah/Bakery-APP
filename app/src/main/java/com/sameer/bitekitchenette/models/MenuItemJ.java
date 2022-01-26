package com.sameer.bitekitchenette.models;

import java.util.ArrayList;

public class MenuItemJ {

    public int id = -1;
    public String itemName;
    public ArrayList<PackSizeJ> packSizes = new ArrayList();
    public boolean deal = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ArrayList<PackSizeJ> getPackSizes() {
        return packSizes;
    }

    public void setPackSizes(ArrayList<PackSizeJ> packSizes) {
        this.packSizes = packSizes;
    }

    public boolean getDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }
}
