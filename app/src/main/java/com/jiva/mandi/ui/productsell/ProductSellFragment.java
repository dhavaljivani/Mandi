package com.jiva.mandi.ui.productsell;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jiva.mandi.R;
import com.jiva.mandi.data.model.UserResponse;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.databinding.FragmentProductSellBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.ui.register.RegisterFragment;
import com.jiva.mandi.ui.register.VillageSpinnerAdapter;
import com.jiva.mandi.utils.AppUtils;
import com.jiva.mandi.utils.CollectionUtils;
import com.jiva.mandi.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import kotlin.collections.CollectionsKt;


public class ProductSellFragment extends BaseFragment<FragmentProductSellBinding, ProductSellViewModel>
        implements ProductSellNavigator, AdapterView.OnItemSelectedListener {

    FragmentProductSellBinding mFragmentProductSellBinding;
    private int[] villageId;
    String[] villageName;
    private final List<UserResponse> userResponseList = new ArrayList<>();
    private final List<Village> villageList = new ArrayList<>();

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
        //Set on click listener on clickable view.
        mFragmentProductSellBinding.btnSell.setOnClickListener(v -> {
            if (isFormValid()) {
                Navigation.findNavController(v).navigate(R.id.action_productSellFragment_to_productSoldFragment);
            }
        });

        mViewModel.getVillageList().observe(getViewLifecycleOwner(), this::setUpVillageSpinner);
        mViewModel.getUserList().observe(getViewLifecycleOwner(), responseList -> {
            userResponseList.addAll(responseList);
            setUpAutocompleteTextView();
        });

        mFragmentProductSellBinding.edtWeight.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtWeight));
        mFragmentProductSellBinding.edtSellerName.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtSellerName));
        mFragmentProductSellBinding.edtCardId.addTextChangedListener(
                new TextFieldValidation(mFragmentProductSellBinding.edtCardId));


    }

    private void setUpAutocompleteTextView() {
        String[] nameList = new String[userResponseList.size()];
        String[] cardIdList = new String[userResponseList.size()];
        for (int i = 0; i < userResponseList.size(); i++) {
            nameList[i] = userResponseList.get(i).getName();
            cardIdList[i] = userResponseList.get(i).getLoyaltyCardId();
        }
        ArrayAdapter<String> sellerNameAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, nameList);
        ArrayAdapter<String> cardIdAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, cardIdList);
        mFragmentProductSellBinding.edtSellerName.setAdapter(sellerNameAdapter);
        mFragmentProductSellBinding.edtCardId.setAdapter(cardIdAdapter);
        mFragmentProductSellBinding.edtSellerName.setOnItemClickListener((parent, view, position, id) -> {
            String cardId = userResponseList.get(position).getLoyaltyCardId();
            mFragmentProductSellBinding.edtCardId.setText(cardId);
        });

        mFragmentProductSellBinding.edtCardId.setOnItemClickListener((parent, view, position, id) -> {
            String name = userResponseList.get(position).getName();
            mFragmentProductSellBinding.edtSellerName.setText(name);
        });
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setUpVillageSpinner(List<Village> villageList) {
        if (CollectionUtils.isNotEmpty(villageList)) {
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
        int index = Arrays.asList(villageName)
                .indexOf(mViewModel.getProductSellRequest().getVillageName());
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
                if (view.getId() == R.id.edtWeight) {
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
                } else if (view.getId() == R.id.edtSellerName) {
                    validateField(mViewModel.getProductSellRequest().getSellerName(),
                            mFragmentProductSellBinding.sellerNameTextField,
                            mFragmentProductSellBinding.edtSellerName,
                            null,
                            getString(R.string.error_seller_name)
                    );
                } else if (view.getId() == R.id.edtCardId) {
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
}