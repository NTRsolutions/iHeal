package com.sismatix.iheal.Model;

public class Cart_Delivery_Model {
    String delivery_type;
    String delivery_price;

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public Cart_Delivery_Model(String delivery_type, String delivery_price) {
        this.delivery_type = delivery_type;
        this.delivery_price = delivery_price;
    }


}
