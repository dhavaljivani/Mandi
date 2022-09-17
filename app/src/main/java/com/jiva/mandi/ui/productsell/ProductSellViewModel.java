package com.jiva.mandi.ui.productsell;

import android.text.TextUtils;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.LoginRequest;
import com.jiva.mandi.data.model.db.User;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.ui.register.RegisterNavigator;
import com.jiva.mandi.utils.AppUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductSellViewModel extends BaseViewModel<RegisterNavigator> {

    public ProductSellViewModel(DataManager dataManager) {
        super(dataManager);
    }
}
