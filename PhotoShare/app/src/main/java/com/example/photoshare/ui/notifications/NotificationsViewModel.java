package com.example.photoshare.ui.notifications;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photoshare.model.caogao.CaoGaoModel;
import com.example.photoshare.model.caogao.RecordsBean;
import com.example.photoshare.service.PhotoService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<RecordsBean>> minelist=new MutableLiveData<List<RecordsBean>>();

    List<RecordsBean> minephotolist;

    private static final String TAG = "NotificationsViewModel";

    private String userid;

    public NotificationsViewModel(String userid) {
        this.userid=userid;
        minephotolist=new ArrayList<>();
        getMineShareDate();
    }
    //ViewModel中的传参不能直接通过new一个ViewModel来实现，这样做ViewModel的生命周期就受Activity的影响
    //需要借助ViewModelProvider.Factory来实现传参

    public LiveData<List<RecordsBean>> getArryList(){
        return  minelist;
    }

    public void getMineShareDate(){

        PhotoService photoService= RetrofitUtils.getInstance().getRetrofit().create(PhotoService.class);
        Call<CaoGaoModel> recordsBeanCall=photoService.myself(0,8,userid);
        Log.d(TAG, "getMineShareDate: "+userid);

        recordsBeanCall.enqueue(new Callback<CaoGaoModel>() {
            @Override
            public void onResponse(Call<CaoGaoModel> call, Response<CaoGaoModel> response) {
                CaoGaoModel caoGaoModel=response.body();
                if (caoGaoModel.getData()!=null){
                    minephotolist.addAll(caoGaoModel.getData().getRecords());
                }
                minelist.setValue(minephotolist);
                Log.d(TAG, "onResponse: "+minephotolist+userid);
            }

            @Override
            public void onFailure(Call<CaoGaoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}