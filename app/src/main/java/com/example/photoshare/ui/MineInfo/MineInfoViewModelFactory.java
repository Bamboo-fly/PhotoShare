package com.example.photoshare.ui.MineInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MineInfoViewModelFactory implements ViewModelProvider.Factory {

    private String userid;

    public MineInfoViewModelFactory(String user){
        this.userid=user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MineInfoViewModel(userid);
    }
}
