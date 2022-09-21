package com.jiva.mandi.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jiva.mandi.data.datamagager.DataManager;
import com.jiva.mandi.data.model.db.Village;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager mDataManager;


    private final CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    private MutableLiveData<List<Village>> villageList;


    public BaseViewModel(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public void getAllVillages() {
        Disposable disposable = getDataManager().getAllVillages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> villageList.setValue(response), throwable -> {
                    //getNavigator().handleError(throwable);
                });
        getCompositeDisposable().add(disposable);
    }

    public MutableLiveData<List<Village>> getVillageList() {
        if (villageList == null) {
            villageList = new MutableLiveData<>();
        }
        return villageList;
    }

}
