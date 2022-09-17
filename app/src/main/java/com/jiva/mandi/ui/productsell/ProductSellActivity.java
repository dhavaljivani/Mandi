package com.jiva.mandi.ui.productsell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;

import com.jiva.mandi.BR;
import com.jiva.mandi.R;
import com.jiva.mandi.databinding.ActivityProductSellBinding;
import com.jiva.mandi.di.component.ActivityComponent;
import com.jiva.mandi.ui.base.BaseActivity;
import com.jiva.mandi.ui.productsold.ProductSoldActivity;
import com.jiva.mandi.ui.register.VillageSpinnerAdapter;

public class ProductSellActivity extends BaseActivity<ActivityProductSellBinding, ProductSellViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.productSellViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_sell;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar(true,getString(R.string.product_sell_title));
        ActivityProductSellBinding mActivitySellBinding = getViewDataBinding();

        setUpVillageSpinner();

        //Set on click listener on clickable view.
        mActivitySellBinding.btnSell.setOnClickListener(v -> {
            moveActivity(this, ProductSoldActivity.class,false);
        });

    }

    private void setUpVillageSpinner() {
        VillageSpinnerAdapter villageSpinnerAdapter = new VillageSpinnerAdapter(this,
                getResources().getStringArray(R.array.villageArray), R.layout.villag_dropdown_text,
                R.layout.village_name_dropdown);
        AppCompatSpinner villageSpinner = findViewById(R.id.spVillage);
        villageSpinner.setAdapter(villageSpinnerAdapter);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}