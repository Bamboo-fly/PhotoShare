package com.example.photoshare.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactoyr implements ViewModelProvider.Factory {

    private String userid;

    public HomeViewModelFactoyr(String userid){
        this.userid=userid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new HomeViewModel(userid);
    }
}
