package com.example.photoshare.ui.notifications;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NotificationsViewModelFactory implements ViewModelProvider.Factory {

    private String userid;

    public NotificationsViewModelFactory(String user){
        this.userid=user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new NotificationsViewModel(userid);
    }
}
