package com.jiva.mandi.data.model;

public class ProductSellRequest {

    private String sellerName;
    private String loyaltyCardId;
    private String weight;
    private String villageName;
    private int villageId;
    private double sellingPrice;
    private double loyaltyIndex = 1.12;
    private String finalPrice ="0";


    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getLoyaltyCardId() {
        return loyaltyCardId;
    }

    public void setLoyaltyCardId(String loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getLoyaltyIndex() {
        return loyaltyIndex;
    }

    public void setLoyaltyIndex(double loyaltyIndex) {
        this.loyaltyIndex = loyaltyIndex;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
}
