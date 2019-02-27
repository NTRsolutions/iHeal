package com.sismatix.iheal.Model;

public class My_order_model {
    String increment_id;
    String created_at;
    String name;
    String grand_total;

    public My_order_model(String increment_id, String created_at, String name, String grand_total) {
        this.increment_id = increment_id;
        this.created_at = created_at;
        this.name = name;
        this.grand_total = grand_total;
    }
    public String getIncrement_id() {
        return increment_id;
    }
    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

}
