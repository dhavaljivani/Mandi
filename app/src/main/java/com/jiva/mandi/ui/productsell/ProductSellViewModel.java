package com.jiva.mandi.ui.productsell;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.ProductSellRequest;
import com.jiva.mandi.data.model.UserResponse;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.ui.register.RegisterNavigator;
import com.jiva.mandi.utils.AppConstants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductSellViewModel extends BaseViewModel<ProductSellNavigator> {

    private ProductSellRequest productSellRequest;
    private MutableLiveData<List<UserResponse>> userList;
    private String loggedInUserData;


    public ProductSellViewModel(DataManager dataManager) {
        super(dataManager);
        productSellRequest = new ProductSellRequest();
        getAllVillages();
        getAllUsers();
        String loggedInUserData = getDataManager().getLoggedInUser();
        setLoggedInUserData(loggedInUserData);
    }

    public void getAllUsers() {
        Disposable disposable = getDataManager().getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponseList -> {
                    userList.setValue(userResponseList);
                }, throwable -> {
                    getNavigator().handleError(throwable);
                });
        getCompositeDisposable().add(disposable);
    }

    public void getLoggedInUserDetails() {
        Disposable disposable = getDataManager().getLoggedInUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if (user != null && user.getUserId() != 0) {
                        productSellRequest.setSellerName(user.getName());
                        productSellRequest.setLoyaltyCardId(user.getLoyaltyCardId());
                        productSellRequest.setVillageName(user.getVillageName());
                        productSellRequest.setVillageId(user.getVillageId());
                        productSellRequest.setFinalPrice("0");
                        productSellRequest.setWeight("");
                        productSellRequest.setSellingPrice(Double.parseDouble(user.getSellingPrice()));
                    } else {
                        productSellRequest = new ProductSellRequest();
                    }
                    setLoyaltyIndex();
                    getNavigator().refreshView();
                }, throwable -> {
                    getNavigator().handleError(throwable);
                });
        getCompositeDisposable().add(disposable);
    }

    public ProductSellRequest getProductSellRequest() {
        return productSellRequest;
    }

    public void setProductSellRequest(ProductSellRequest productSellRequest) {
        this.productSellRequest = productSellRequest;
    }

    public MutableLiveData<List<UserResponse>> getUserList() {
        if (userList == null) {
            userList = new MutableLiveData<>();
        }
        return userList;
    }

    public String getLoggedInUserData() {
        return loggedInUserData;
    }

    public void setLoggedInUserData(String loggedInUserData) {
        this.loggedInUserData = loggedInUserData;
    }

    public void setUserList(MutableLiveData<List<UserResponse>> userList) {
        this.userList = userList;
    }

    private void setLoyaltyIndex() {
        if (!TextUtils.isEmpty(getLoggedInUserData())) {
            productSellRequest.setLoyaltyIndex(AppConstants.REGISTERED_USER_LOYALTY_INDEX);
        } else {
            productSellRequest.setLoyaltyIndex(AppConstants.UNREGISTERED_USER_LOYALTY_INDEX);
        }
    }


}
