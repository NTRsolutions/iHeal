package com.sismatix.iheal.Model;

public class Product_Grid_Model {
    String product_image,product_price,producr_title;

    public Product_Grid_Model(String product_image, String product_price, String producr_title) {
        this.product_image = product_image;
        this.product_price = product_price;
        this.producr_title = producr_title;
    }

    public String getProduct_image() {

        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProducr_title() {
        return producr_title;
    }

    public void setProducr_title(String producr_title) {
        this.producr_title = producr_title;
    }
}
