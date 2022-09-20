package com.jiva.mandi.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.ui.base.ViewModelProviderFactory;
import com.jiva.mandi.ui.login.LoginViewModel;
import com.jiva.mandi.ui.productsell.ProductSellViewModel;
import com.jiva.mandi.ui.productsold.ProductSoldViewModel;
import com.jiva.mandi.ui.register.RegisterViewModel;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {

    private final BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, Gson gson) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(dataManager, gson);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(LoginViewModel.class);
    }

    @Provides
    RegisterViewModel provideRegisterViewModel(DataManager dataManager, Gson gson) {
        Supplier<RegisterViewModel> supplier = () -> new RegisterViewModel(dataManager, gson);
        ViewModelProviderFactory<RegisterViewModel> factory = new ViewModelProviderFactory<>(RegisterViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(RegisterViewModel.class);
    }

    @Provides
    ProductSellViewModel provideProductSellViewModel(DataManager dataManager) {
        Supplier<ProductSellViewModel> supplier = () -> new ProductSellViewModel(dataManager);
        ViewModelProviderFactory<ProductSellViewModel> factory = new ViewModelProviderFactory<>(ProductSellViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProductSellViewModel.class);
    }

    @Provides
    ProductSoldViewModel provideProductSoldViewModel(DataManager dataManager) {
        Supplier<ProductSoldViewModel> supplier = () -> new ProductSoldViewModel(dataManager);
        ViewModelProviderFactory<ProductSoldViewModel> factory = new ViewModelProviderFactory<>(ProductSoldViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProductSoldViewModel.class);
    }

}
