package com.hansliao.springboot_mall.dto;

import javax.validation.constraints.NotNull;

public class BuyItem {
    
    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }


}
