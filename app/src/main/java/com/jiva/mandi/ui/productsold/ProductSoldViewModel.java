package com.jiva.mandi.ui.productsold;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.ui.register.RegisterNavigator;

public class ProductSoldViewModel extends BaseViewModel<RegisterNavigator> {

    private String sellerName;
    private String finalPrice;
    private String totalWeight;

    public ProductSoldViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }
}
