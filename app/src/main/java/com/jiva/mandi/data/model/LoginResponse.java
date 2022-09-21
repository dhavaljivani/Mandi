package com.jiva.mandi.data.model;

public class LoginResponse {

    private int userId;
    private String name;
    private String loyaltyCardId;
    private int villageId;
    private String villageName;
    private String sellingPrice;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoyaltyCardId() {
        return loyaltyCardId;
    }

    public void setLoyaltyCardId(String loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }
}
