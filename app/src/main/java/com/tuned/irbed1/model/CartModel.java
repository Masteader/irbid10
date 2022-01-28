package com.tuned.irbed1.model;

public class CartModel {
    private String Id ;
    private int count;
    private String Id_items;
    private String Name;
    private String Image;
    private int Price;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId_items() {
        return Id_items;
    }

    public void setId_items(String id_items) {
        Id_items = id_items;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
