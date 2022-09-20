package com.jiva.mandi.ui.productsell;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jiva.mandi.R;
import com.jiva.mandi.data.model.UserResponse;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.databinding.FragmentProductSellBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.ui.register.VillageSpinnerAdapter;
import com.jiva.mandi.utils.AlertDialogHelper;
import com.jiva.mandi.utils.AppUtils;
import com.jiva.mandi.utils.CollectionUtils;
import com.jiva.mandi.utils.SnackBarUtils;
import com.jiva.mandi.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import kotlin.collections.CollectionsKt;


public class ProductSellFragment extends BaseFragment<FragmentProductSellBinding, ProductSellViewModel>
        implements ProductSellNavigator, AdapterView.OnItemSelectedListener, AlertDialogHelper.DialogButtonClickListener {

    FragmentProductSellBinding mFragmentProductSellBinding;
    private int[] villageId;
    String[] villageName;
    private final List<UserResponse> userResponseList = new ArrayList<>();
    private final List<Village> villageList = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce = false;

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
        mViewModel.setNavigator(this);
        mViewModel.getVillageList().observe(getViewLifecycleOwner(), this::setUpVillageSpinner);
        mViewModel.getUserList().observe(getViewLifecycleOwner(), responseList -> {
            userResponseList.clear();
            userResponseList.addAll(responseList);
            setUpAutocompleteTextView();
        });

        mFragmentProductSellBinding.edtWeight.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtWeight));
        mFragmentProductSellBinding.edtSellerName.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtSellerName));
        mFragmentProductSellBinding.edtCardId.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtCardId));


        setUpMenu(view);

        //Set on click listener on clickable view.
        mFragmentProductSellBinding.btnSell.setOnClickListener(v -> {
            if (isFormValid()) {
                hideKeyboard();
                AlertDialogHelper.showDialog(getContext(), null, getString(R.string.sell_confirmation)
                        , getString(R.string.yes), getString(R.string.no), false,
                        ProductSellFragment.this, AlertDialogHelper.DialogIdentifier.SELL_DIALOG);
            }
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (doubleBackToExitPressedOnce) {
                    requireActivity().finish();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                SnackBarUtils.showSnackBar(view, getString(R.string.exitMessage), Snackbar.LENGTH_SHORT);
                Completable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(completableObserver());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

    }

    private void setUpAutocompleteTextView() {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> cardIdList = new ArrayList<>();
        for (UserResponse userResponse : userResponseList) {
            nameList.add(userResponse.getName());
            cardIdList.add(userResponse.getLoyaltyCardId());
        }

        AutoCompleteAdapter sellerNameAdapter = new AutoCompleteAdapter(getContext());
        sellerNameAdapter.addAllData(nameList);
        sellerNameAdapter.notifyDataSetChanged();
        AutoCompleteAdapter cardIdAdapter = new AutoCompleteAdapter(getContext());
        cardIdAdapter.addAllData(cardIdList);
        cardIdAdapter.notifyDataSetChanged();

        mFragmentProductSellBinding.edtSellerName.setAdapter(sellerNameAdapter);
        mFragmentProductSellBinding.edtCardId.setAdapter(cardIdAdapter);
        mFragmentProductSellBinding.edtSellerName.setOnItemClickListener((parent, view, position, id) -> {
            String cardId = userResponseList.get(position).getLoyaltyCardId();
            mFragmentProductSellBinding.edtCardId.setText(cardId);
            ValidationUtil.removeErrorFromTextLayout(mFragmentProductSellBinding.cardIdTextField);
        });

        mFragmentProductSellBinding.edtCardId.setOnItemClickListener((parent, view, position, id) -> {
            String name = userResponseList.get(position).getName();
            mFragmentProductSellBinding.edtSellerName.setText(name);
            ValidationUtil.removeErrorFromTextLayout(mFragmentProductSellBinding.sellerNameTextField);
        });
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setUpVillageSpinner(List<Village> villageList) {
        if (CollectionUtils.isNotEmpty(villageList)) {
            this.villageList.clear();
            this.villageList.addAll(villageList);
            villageName = new String[villageList.size() + 1];
            villageId = new int[villageList.size() + 1];
            villageName[0] = getString(R.string.select_village);
            villageId[0] = 0;
            for (int i = 0; i < villageList.size(); i++) {
                villageName[i + 1] = villageList.get(i).name;
                villageId[i + 1] = villageList.get(i).id;
            }
            VillageSpinnerAdapter villageSpinnerAdapter = new VillageSpinnerAdapter(getBaseActivity(),
                    villageName, R.layout.villag_dropdown_text,
                    R.layout.village_name_dropdown);
            mFragmentProductSellBinding.spVillage.setAdapter(villageSpinnerAdapter);
            mFragmentProductSellBinding.spVillage.setOnItemSelectedListener(this);
        }
        mViewModel.getLoggedInUserDetails();
    }

    private boolean isFormValid() {
        boolean isNameValid = ValidationUtil.isNameIsValid(mViewModel.getProductSellRequest().getSellerName());
        boolean isCardIdValid = ValidationUtil.isNameIsValid(mViewModel.getProductSellRequest().getLoyaltyCardId());
        boolean isWightValid = ValidationUtil.isNameIsValid(mViewModel.getProductSellRequest().getWeight());
        boolean isVillageId = mViewModel.getProductSellRequest().getVillageId() != 0;
        boolean isValid = true;
        if (!isNameValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentProductSellBinding.edtSellerName,
                    mFragmentProductSellBinding.sellerNameTextField,
                    getString(R.string.error_seller_name));
            isValid = false;
        }

        if (!isCardIdValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentProductSellBinding.edtCardId,
                    mFragmentProductSellBinding.cardIdTextField,
                    getString(R.string.error_card_id));
            isValid = false;
        }


        if (!isWightValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentProductSellBinding.edtWeight,
                    mFragmentProductSellBinding.weightTextField,
                    getString(R.string.error_weight));
            isValid = false;
        }

        if (!isVillageId) {
            mFragmentProductSellBinding.tvSelectVillageError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void handleError(Throwable throwable) {
        AppUtils.handleException(throwable);
    }

    @Override
    public void refreshView() {
        int index = 0;
        if (!TextUtils.isEmpty(mViewModel.getProductSellRequest().getVillageName())) {
            index = Arrays.asList(villageName)
                    .indexOf(mViewModel.getProductSellRequest().getVillageName());
        }

        mFragmentProductSellBinding.spVillage.setSelection(index);
        executeBinding();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int selectedVillageId = villageId[position];
        mViewModel.getProductSellRequest().setVillageId(selectedVillageId);
        if (selectedVillageId != 0) {
            mFragmentProductSellBinding.tvSelectVillageError.setVisibility(View.GONE);
        }
        if (selectedVillageId != 0) {
            List<Village> filteredVillageList = CollectionsKt.filter(villageList, village -> (village.getId()) == selectedVillageId);
            if (CollectionUtils.isNotEmpty(filteredVillageList)) {
                mViewModel.getProductSellRequest().setVillageName(filteredVillageList.get(0).getName());
                mViewModel.getProductSellRequest().setVillageId(filteredVillageList.get(0).getId());
                mViewModel.getProductSellRequest().setSellingPrice(filteredVillageList.get(0).getSellingPrice());

                if (!TextUtils.isEmpty(mViewModel.getProductSellRequest().getWeight())) {
                    String finalPrice = calculateFinalPrice(Double.parseDouble(mViewModel.getProductSellRequest().getWeight()),
                            mViewModel.getProductSellRequest().getSellingPrice(),
                            mViewModel.getProductSellRequest().getLoyaltyIndex());
                    mViewModel.getProductSellRequest().setFinalPrice(finalPrice);
                    executeBinding();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * TextWatcher for all the textInputField
     */
    private class TextFieldValidation implements TextWatcher {
        private final View view;

        /**
         * @param view The view for the get id for compare in afterTextChanged method.
         */
        public TextFieldValidation(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                if (view.getId() == R.id.edtWeight && mFragmentProductSellBinding.edtWeight.hasFocus()) {
                    validateField(mViewModel.getProductSellRequest().getWeight(),
                            mFragmentProductSellBinding.weightTextField,
                            null,
                            mFragmentProductSellBinding.edtWeight,
                            getString(R.string.error_weight));
                    if (!TextUtils.isEmpty(charSequence.toString())) {
                        String finalPrice = calculateFinalPrice(Double.parseDouble(charSequence.toString()),
                                mViewModel.getProductSellRequest().getSellingPrice(),
                                mViewModel.getProductSellRequest().getLoyaltyIndex());
                        mViewModel.getProductSellRequest().setFinalPrice(finalPrice);
                    } else {
                        mViewModel.getProductSellRequest().setFinalPrice("0");
                    }
                    executeBinding();
                } else if (view.getId() == R.id.edtSellerName && mFragmentProductSellBinding.edtSellerName.hasFocus()) {
                    validateField(mViewModel.getProductSellRequest().getSellerName(),
                            mFragmentProductSellBinding.sellerNameTextField,
                            mFragmentProductSellBinding.edtSellerName,
                            null,
                            getString(R.string.error_seller_name)
                    );
                } else if (view.getId() == R.id.edtCardId && mFragmentProductSellBinding.edtCardId.hasFocus()) {
                    validateField(mViewModel.getProductSellRequest().getLoyaltyCardId(),
                            mFragmentProductSellBinding.cardIdTextField,
                            mFragmentProductSellBinding.edtCardId,
                            null,
                            getString(R.string.error_card_id)
                    );
                }
            } catch (Exception e) {
                AppUtils.handleException(e);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Called after text changed
        }
    }

    private String calculateFinalPrice(double weight, double sellingPrice, double loyaltyIndex) {
        double weightInKg = convertTonesToKg(weight);
        double totalWeight = weightInKg * sellingPrice * loyaltyIndex;
        return String.format(Locale.US, "%,.2f", totalWeight);
        //return new DecimalFormat("##.##").format(totalWeight);
    }

    private double convertTonesToKg(double weight) {
        return weight * 1000;
    }

    private void executeBinding() {
        mFragmentProductSellBinding.setProductSellViewModel(mViewModel);
        mFragmentProductSellBinding.executePendingBindings();
    }

    private void validateField(String inputString, TextInputLayout textInputLayout,
                               MaterialAutoCompleteTextView textView,
                               TextInputEditText inputEditText,
                               String message) {
        View view;
        if (textView == null) {
            view = inputEditText;
        } else {
            view = textView;
        }

        boolean isValid = ValidationUtil
                .isNameIsValid(inputString);
        if (isValid) {
            ValidationUtil.removeErrorFromTextLayout(textInputLayout);
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(view,
                    textInputLayout,
                    message);
        }
    }

    private void setUpMenu(View view) {
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                menuInflater.inflate(R.menu.menu_main, menu);
                MenuItem log_in_item = menu.findItem(R.id.action_login);
                MenuItem log_out_item = menu.findItem(R.id.action_log_out);
                log_out_item.setVisible(!TextUtils.isEmpty(mViewModel.getLoggedInUserData()));
                log_in_item.setVisible(TextUtils.isEmpty(mViewModel.getLoggedInUserData()));
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_log_out) {
                    AlertDialogHelper.showDialog(getContext(), null, getString(R.string.logout_confirmation)
                            , getString(R.string.yes), getString(R.string.no), false,
                            ProductSellFragment.this, AlertDialogHelper.DialogIdentifier.LOGOUT_DIALOG);

                } else {
                    Navigation.findNavController(view).popBackStack(R.id.productSellFragment, true);
                    Navigation.findNavController(view).navigate(R.id.loginFragment);
                }
                return true;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onPositiveButtonClicked(int dialogIdentifier) {
        if (dialogIdentifier == AlertDialogHelper.DialogIdentifier.LOGOUT_DIALOG) {
            if (getView() != null) {
                mViewModel.getDataManager().clearPreferences();
                Navigation.findNavController(getView()).popBackStack(R.id.productSellFragment, true);
                Navigation.findNavController(getView()).navigate(R.id.loginFragment);
            }
        } else if (dialogIdentifier == AlertDialogHelper.DialogIdentifier.SELL_DIALOG) {
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.arg_seller_name), mViewModel.getProductSellRequest().getSellerName());
            bundle.putString(getString(R.string.arg_total_weight), mViewModel.getProductSellRequest().getWeight());
            bundle.putString(getString(R.string.arg_total_amount), mViewModel.getProductSellRequest().getFinalPrice());
            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_productSellFragment_to_productSoldFragment, bundle);
            }
        }
    }

    @Override
    public void onNegativeButtonClicked(int dialogIdentifier) {

    }

    private CompletableObserver completableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                doubleBackToExitPressedOnce = false;
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.handleException(e);
            }
        };
    }


}