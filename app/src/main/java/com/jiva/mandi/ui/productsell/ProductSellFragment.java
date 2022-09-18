package com.jiva.mandi.ui.productsell;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import com.jiva.mandi.R;
import com.jiva.mandi.databinding.FragmentProductSellBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.ui.register.VillageSpinnerAdapter;


public class ProductSellFragment extends BaseFragment<FragmentProductSellBinding, ProductSellViewModel> {

    FragmentProductSellBinding mFragmentProductSellBinding;

    @Override
    public int getBindingVariable() {
        return BR.productSellViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_sell;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProductSellBinding = getViewDataBinding();
        setUpVillageSpinner();
        //Set on click listener on clickable view.
        mFragmentProductSellBinding.btnSell.setOnClickListener(v -> Navigation.findNavController(v).
                navigate(R.id.action_productSellFragment_to_productSoldFragment));
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setUpVillageSpinner() {
        VillageSpinnerAdapter villageSpinnerAdapter = new VillageSpinnerAdapter(getBaseActivity(),
                getResources().getStringArray(R.array.villageArray), R.layout.villag_dropdown_text,
                R.layout.village_name_dropdown);
        mFragmentProductSellBinding.spVillage.setAdapter(villageSpinnerAdapter);
    }
}