package com.jiva.mandi.ui.productsold;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityProductSellBinding;
import com.jiva.mandi.databinding.ActivityProductSoldBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.productsell.ProductSellViewModel;

public class ProductSoldActivity extends BaseActivity<ActivityProductSoldBinding, ProductSoldViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.productSoldViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_sold;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar(true, getString(R.string.product_sold_title));
        ActivityProductSoldBinding mActivitySellBinding = getViewDataBinding();
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}