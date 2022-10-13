package com.example.photoshare.ui.MineLike;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MineLikeViewModelFactoyr implements ViewModelProvider.Factory {

    private String userid;

    public MineLikeViewModelFactoyr(String userid){
        this.userid=userid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MineLikeViewModel(userid);
    }
}
