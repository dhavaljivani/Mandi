package com.jiva.mandi.ui.main;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.ui.base.BaseViewModel;
import com.jiva.mandi.ui.register.RegisterNavigator;

public class MainViewModel extends BaseViewModel<RegisterNavigator> {

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
    }
}
