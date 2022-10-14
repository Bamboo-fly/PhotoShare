package com.example.photoshare.ui.Find;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.share.ShareModel;
import com.example.photoshare.service.ShareService;
import com.example.photoshare.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindViewModel extends ViewModel {


    private  MutableLiveData<List<RecordsBean>> newsList = new MutableLiveData<>();

    List<RecordsBean> shareModellist;

    private static final String TAG = "DashboardViewModel";

    private String userId;
    //用来借助Factory传递userid到ViewModel层

    public FindViewModel(String userId) {
        this.userId=userId;
        shareModellist=new ArrayList<>();
        getShareDate();
    }

    public LiveData<List<RecordsBean>> getArryList(){
        return newsList;
    }

    public  void getShareDate()  {

        ShareService shareService= RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
        Call<ShareModel> call=shareService.sharedate(0,30,userId);
        //点赞功能无法实现是因为这里的Userid有问题

        call.enqueue(new Callback<ShareModel>() {
            @Override
            public void onResponse(Call<ShareModel> call, Response<ShareModel> response) {
                ShareModel shareModel = response.body();
                Log.d(TAG, "onResponse: "+shareModel.getData());

                if (shareModel.getData()!=null){

//                    for (int i=0;i<shareModel.getData().getRecords().size();i++){
//                        shareModel.getData().getRecords().get(i).getImageUrlList();
//                    }
                    shareModellist.addAll(shareModel.getData().getRecords());


                }else{
                    Log.d(TAG, "请求次数用尽");
                }
                newsList.setValue(shareModellist);
            }

            @Override
            public void onFailure(Call<ShareModel> call, Throwable t) {
                    t.printStackTrace();
            }
        });


    }
}