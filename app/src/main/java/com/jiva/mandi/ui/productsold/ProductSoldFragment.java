package com.jiva.mandi.ui.productsold;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import com.jiva.mandi.R;
import com.jiva.mandi.databinding.FragmentProductSoldBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;


public class ProductSoldFragment extends BaseFragment<FragmentProductSoldBinding, ProductSoldViewModel> {

    FragmentProductSoldBinding mFragmentProductSoldBinding;

    @Override
    public int getBindingVariable() {
        return BR.productSoldViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_sold;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProductSoldBinding = getViewDataBinding();
        //Set on click listener on clickable view.
        mFragmentProductSoldBinding.btnSellMore.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}