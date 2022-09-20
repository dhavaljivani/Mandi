package com.jiva.mandi.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jiva.mandi.R;
import com.jiva.mandi.data.model.db.Village;
import com.jiva.mandi.databinding.FragmentRegisterBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.utils.AppUtils;
import com.jiva.mandi.utils.CollectionUtils;
import com.jiva.mandi.utils.SnackBarUtils;
import com.jiva.mandi.utils.ValidationUtil;

import java.util.List;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel>
        implements View.OnClickListener, RegisterNavigator, View.OnFocusChangeListener {

    FragmentRegisterBinding mFragmentRegisterBinding;
    private int[] villageId;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public int getBindingVariable() {
        return BR.registerViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRegisterBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        mViewModel.getVillageList().observe(getViewLifecycleOwner(), this::setUpVillageSpinner);
        setup();
    }

    /**
     * Basic setup.
     */
    private void setup() {
        //Set on click listener on clickable view.
        mFragmentRegisterBinding.btnSignUp.setOnClickListener(this);
        mFragmentRegisterBinding.ivBack.setOnClickListener(this);
        mFragmentRegisterBinding.edtMobileNumber.setOnFocusChangeListener(this);
        mFragmentRegisterBinding.edtPassword.setOnFocusChangeListener(this);

        mFragmentRegisterBinding.edtFullName.addTextChangedListener(
                new TextFieldValidation(mFragmentRegisterBinding.edtFullName));
        mFragmentRegisterBinding.edtMobileNumber.addTextChangedListener(
                new TextFieldValidation(mFragmentRegisterBinding.edtMobileNumber));
        mFragmentRegisterBinding.edtPassword.addTextChangedListener(
                new TextFieldValidation(mFragmentRegisterBinding.edtPassword));


    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    /**
     * Setup village list in spinner.
     *
     * @param villageList list of villages.
     */
    private void setUpVillageSpinner(List<Village> villageList) {
        if (CollectionUtils.isNotEmpty(villageList)) {
            String[] villageName = new String[villageList.size() + 1];
            villageId = new int[villageList.size() + 1];
            villageName[0] = getString(R.string.select_village);
            villageId[0] = 0;
            for (int i = 0; i < villageList.size(); i++) {
                villageName[i + 1] = villageList.get(i).name;
                villageId[i + 1] = villageList.get(i).id;
            }
            VillageSpinnerAdapter villageSpinnerAdapter = new VillageSpinnerAdapter(getMContext(),
                    villageName, R.layout.villag_dropdown_text,
                    R.layout.village_name_dropdown);
            mFragmentRegisterBinding.spVillage.setAdapter(villageSpinnerAdapter);

            mFragmentRegisterBinding.spVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectedVillageId = villageId[position];
                    mViewModel.getUser().setVillageId(selectedVillageId);
                    if (selectedVillageId != 0) {
                        mFragmentRegisterBinding.tvSelectVillageError.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            Navigation.findNavController(v).popBackStack();
        } else if (v.getId() == R.id.btnSignUp) {
            if (isFormValid()) {
                mViewModel.checkAndCreateUser();
            }
        }
    }

    /**
     * Check registration form validation.
     *
     * @return true/false based on input data.
     */
    private boolean isFormValid() {
        boolean isNameValid = ValidationUtil.isNameIsValid(mViewModel.getUser().getName());
        boolean isMobileValid = ValidationUtil.isMobileNumberLengthValid(mViewModel
                .getUser().getMobileNumber());
        boolean isPwdValid = ValidationUtil.isPasswordIsValid(mViewModel.getUser().getPassword());
        boolean isVillageId = mViewModel.getUser().getVillageId() != 0;
        boolean isValid = true;
        if (!isNameValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtFullName,
                    mFragmentRegisterBinding.fullNameTextField,
                    getString(R.string.error_name));
            isValid = false;
        }

        if (!isMobileValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtMobileNumber,
                    mFragmentRegisterBinding.mobileNumberTextField,
                    getString(R.string.error_mobile_valid));
            isValid = false;
        }


        if (!isPwdValid) {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtPassword,
                    mFragmentRegisterBinding.passwordTextField,
                    getString(R.string.error_password));
            isValid = false;
        }

        if (!isVillageId) {
            mFragmentRegisterBinding.tvSelectVillageError.setVisibility(View.VISIBLE);
            isValid = false;
        }

        return isValid;
    }

    @SuppressWarnings("ALL")
    @Override
    public void handleError(Throwable throwable) {
        AppUtils.handleException(throwable);
    }

    @SuppressWarnings("ALL")
    @Override
    public void SignUpSuccess() {
        if (getView() != null) {
            SnackBarUtils.showSnackBar(getView(), getString(R.string.user_registered_msg), Snackbar.LENGTH_SHORT);
            Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_productSellFragment);
        }
    }

    @SuppressWarnings("ALL")
    @Override
    public void UserAlreadyExist() {
        SnackBarUtils.showSnackBar(getView(), getString(R.string.user_exist_msg), Snackbar.LENGTH_LONG);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v.getId() == R.id.edtMobileNumber) {
                validateName();
            } else if (v.getId() == R.id.edtPassword) {
                validateName();
                validateMobileNumber(false);
            }
        }
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
                if (view.getId() == R.id.edtFullName) {
                    validateName();
                } else if (view.getId() == R.id.edtMobileNumber) {
                    validateMobileNumber(true);
                } else {
                    validatePassword();
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

    /**
     * Validate seller name and set error in text field.
     */
    private void validateName() {
        boolean isValid = ValidationUtil
                .isNameIsValid(mViewModel.getUser().getName());
        if (isValid) {
            ValidationUtil.removeErrorFromTextLayout(mFragmentRegisterBinding.fullNameTextField);
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtFullName,
                    mFragmentRegisterBinding.fullNameTextField,
                    getString(R.string.error_name));
        }
    }

    /**
     * Validate mobile number and set error in text field.
     */
    private void validateMobileNumber(boolean onTextChanged) {
        boolean isValid;
        String message;
        if (onTextChanged) {
            isValid = ValidationUtil.isMobileNumberIsValid(mViewModel.getUser().getMobileNumber());
            message = getString(R.string.error_mobile);
        } else {
            isValid = ValidationUtil.isMobileNumberLengthValid(mViewModel.getUser().getMobileNumber());
            message = getString(R.string.error_mobile_valid);
        }

        if (isValid) {
            ValidationUtil.removeErrorFromTextLayout(mFragmentRegisterBinding.mobileNumberTextField);
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtMobileNumber,
                    mFragmentRegisterBinding.mobileNumberTextField,
                    message);
        }
    }

    /**
     * Validate password and set error in text field.
     */
    private void validatePassword() {
        boolean isValid = ValidationUtil.isPasswordIsValid(mViewModel.getUser().getPassword());
        if (isValid) {
            ValidationUtil.removeErrorFromTextLayout(mFragmentRegisterBinding.passwordTextField);
        } else {
            ValidationUtil.setErrorIntoInputTextLayout(mFragmentRegisterBinding.edtPassword,
                    mFragmentRegisterBinding.passwordTextField,
                    getString(R.string.error_password));
        }
    }
}