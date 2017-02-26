package org.cdsdemo.shoppingcart.model;

import javax.validation.constraints.Min;

public class CartLineInfo {
	 
    private ProductInfo productInfo;
    
    @Min(1)
    private int quantity;
 
    public CartLineInfo() {
        this.quantity = 0;
    }
 
    public ProductInfo getProductInfo() {
        return productInfo;
    }
 
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
 
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
    
}
