package com.example.jangbogo;

import java.io.Serializable;

public class list implements Serializable {
    private String store_name;
    private String store_intro;
    private String store_address;
    private String store_item;
    private String store_phone;
    private String store_time;
    private String store_sale;
    private String store_sell;
    private String review_count;
    private String review_sum;

    public list() {
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_intro() {
        return store_intro;
    }

    public void setStore_intro(String store_intro) {
        this.store_intro = store_intro;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_item() {
        return store_item;
    }

    public void setStore_item(String store_item) {
        this.store_item = store_item;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_time() {
        return store_time;
    }

    public void setStore_time(String store_time) {
        this.store_time = store_time;
    }

    public String getStore_sale() {
        return store_sale;
    }

    public void setStore_sale(String store_sale) {
        this.store_sale = store_sale;
    }

    public String getStore_sell() {
        return store_sell;
    }

    public void setStore_sell(String store_sell) {
        this.store_sell = store_sell;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getReview_sum() {
        return review_sum;
    }

    public void setReview_sum(String review_sum) {
        this.review_sum = review_sum;
    }



}
