package com.example.photoshare.ui.Find;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FindViewModelFactory implements ViewModelProvider.Factory {


    private String userid;

    public FindViewModelFactory(String userid){
        this.userid=userid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new FindViewModel(userid);

    }
}
