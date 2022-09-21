package com.jiva.mandi.ui.productsold;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get data from bundle and set into view model.
        if (getArguments() != null) {
            String sellerName = getArguments().getString(getString(R.string.arg_seller_name), "");
            String finalPrice = getArguments().getString(getString(R.string.arg_total_amount), "");
            String totalWeight = getArguments().getString(getString(R.string.arg_total_weight), "");
            mViewModel.setSellerName(sellerName);
            mViewModel.setTotalWeight(totalWeight);
            mViewModel.setFinalPrice(finalPrice);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProductSoldBinding = getViewDataBinding();
        //Set on click listener on clickable view.
        mFragmentProductSoldBinding.btnSellMore.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        mFragmentProductSoldBinding.tvTitle.setText(HtmlCompat
                .fromHtml(getString(R.string.product_sold_msg, mViewModel.getSellerName())
                        , HtmlCompat.FROM_HTML_MODE_LEGACY));

        mFragmentProductSoldBinding.tvMessage.setText(HtmlCompat
                .fromHtml(getString(R.string.product_sold_price_msg, mViewModel.getFinalPrice(), mViewModel.getTotalWeight())
                        , HtmlCompat.FROM_HTML_MODE_LEGACY));

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}