package com.example.photoshare.ui.MineLike;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photoshare.model.minelike.MineLikeModel;
import com.example.photoshare.service.MineService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineLikeViewModel extends ViewModel {

    private final MutableLiveData<List<com.example.photoshare.model.minelike.RecordsBean>> newsList=new MutableLiveData<>();

    List<com.example.photoshare.model.minelike.RecordsBean> shareModeLiast;

    private String userid;

    public MineLikeViewModel(String userid) {
        this.userid=userid;
        shareModeLiast=new ArrayList<com.example.photoshare.model.minelike.RecordsBean>();
        getLikeDate();
    }

    public LiveData<List<com.example.photoshare.model.minelike.RecordsBean>> getArryList() {
        return newsList;
    }

    public void getLikeDate(){
        MineService mineService= RetrofitUtils.getInstance().getRetrofit().create(MineService.class);
        Call<MineLikeModel> call= mineService.minelike(userid);

        call.enqueue(new Callback<MineLikeModel>() {
            @Override
            public void onResponse(Call<MineLikeModel> call, Response<MineLikeModel> response) {
                MineLikeModel mineLikeModel= response.body();
                if (mineLikeModel.getData()!=null){
                    shareModeLiast.addAll(mineLikeModel.getData().getRecords());
                }
                newsList.setValue(shareModeLiast);
            }

            @Override
            public void onFailure(Call<MineLikeModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}