package com.hansliao.springboot_mall.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class CreateOrderRequest {
    
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setButItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
