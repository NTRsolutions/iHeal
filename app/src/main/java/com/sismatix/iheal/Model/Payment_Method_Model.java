package com.sismatix.iheal.Model;

public class Payment_Method_Model {
    String image,payment_type;

    public Payment_Method_Model(String image, String payment_type) {
        this.image = image;
        this.payment_type = payment_type;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
