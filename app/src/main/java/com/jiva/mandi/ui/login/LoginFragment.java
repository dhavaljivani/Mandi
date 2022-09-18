package com.jiva.mandi.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;
import androidx.room.EmptyResultSetException;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.Gson;
import com.jiva.mandi.R;
import com.jiva.mandi.data.model.LoginResponse;
import com.jiva.mandi.databinding.FragmentLoginBinding;
import com.jiva.mandi.di.component.FragmentComponent;
import com.jiva.mandi.ui.base.BaseFragment;
import com.jiva.mandi.utils.AppUtils;
import com.jiva.mandi.utils.ValidationUtil;

import javax.inject.Inject;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> implements View.OnClickListener,
        LoginNavigator, View.OnFocusChangeListener {

    FragmentLoginBinding mFragmentLoginBinding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentLoginBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setup();
    }

    private void setup() {
        //Set on click listener on clickable view.
        mFragmentLoginBinding.btnLogin.setOnClickListener(this);
        mFragmentLoginBinding.tvRegister.setOnClickListener(this);
        mFragmentLoginBinding.tvSkip.setOnClickListener(this);
        mFragmentLoginBinding.edtPassword.setOnFocusChangeListener(this);

        mFragmentLoginBinding.edtMobileNumber.addTextChangedListener(
                new TextFieldValidation(mFragmentLoginBinding.edtMobileNumber));
        mFragmentLoginBinding.edtPassword.addTextChangedListener(
                new TextFieldValidation(mFragmentLoginBinding.edtPassword));


    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            boolean isMobileValid = ValidationUtil.isMobileNumberLengthValid(mViewModel.getLoginRequest().getUsername());
            boolean isPwdValid = ValidationUtil.isPasswordIsValid(mViewModel.getLoginRequest().getPassword());

            if (!isMobileValid) {
                ValidationUtil.setErrorIntoInputTextLayout(mFragmentLoginBinding.edtMobileNumber,
                        mFragmentLoginBinding.mobileNumberTextField,
                        getString(R.string.error_mobile_valid));
            }


            if (!isPwdValid) {
                ValidationUtil.setErrorIntoInputTextLayout(mFragmentLoginBinding.edtPassword,
                        mFragmentLoginBinding.passwordTextField,
                        getString(R.string.error_password));
            }

            if (isMobileValid && isPwdValid) {
                mViewModel.login();
            }
        } else if (v.getId() == R.id.tvRegister) {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        } else if (v.getId() == R.id.tvSkip) {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_productSellFragment);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            boolean isValid = ValidationUtil
                    .isMobileNumberLengthValid(mViewModel.getLoginRequest().getUsername());
            if (!isValid) {
                ValidationUtil.setErrorIntoInputTextLayout(mFragmentLoginBinding.edtMobileNumber,
                        mFragmentLoginBinding.mobileNumberTextField,
                        getString(R.string.error_mobile_valid));
            }
        }
    }


    @Override
    public void handleError(Throwable throwable) {
        if (throwable instanceof EmptyResultSetException) {
            AppUtils.showToast(getContext(), getString(R.string.login_failed));
        }
    }

    @Override
    public void onLoginSuccess() {
        AppUtils.showToast(getContext(), getString(R.string.login_success));
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_productSellFragment);
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
                if (view.getId() == R.id.edtMobileNumber) {
                    boolean isValid = ValidationUtil.isMobileNumberIsValid(mViewModel.getLoginRequest().getUsername());
                    if (isValid) {
                        ValidationUtil.removeErrorFromTextLayout(mFragmentLoginBinding.mobileNumberTextField);
                    } else {
                        ValidationUtil.setErrorIntoInputTextLayout(mFragmentLoginBinding.edtMobileNumber,
                                mFragmentLoginBinding.mobileNumberTextField,
                                getString(R.string.error_mobile));
                    }
                } else {
                    boolean isValid = ValidationUtil.isPasswordIsValid(mViewModel.getLoginRequest().getPassword());
                    if (isValid) {
                        ValidationUtil.removeErrorFromTextLayout(mFragmentLoginBinding.passwordTextField);
                    } else {
                        ValidationUtil.setErrorIntoInputTextLayout(mFragmentLoginBinding.edtPassword,
                                mFragmentLoginBinding.passwordTextField,
                                getString(R.string.error_password));
                    }
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
}