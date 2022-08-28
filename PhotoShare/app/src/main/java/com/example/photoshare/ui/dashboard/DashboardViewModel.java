package com.example.photoshare.ui.dashboard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
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

public class DashboardViewModel extends ViewModel {

    private  final MutableLiveData<String> mText;

    private  final MutableLiveData<String> userlist ;

    private  MutableLiveData<List<RecordsBean>> newsList = new MutableLiveData<>();

    List<RecordsBean> shareModellist;

    private static final String TAG = "DashboardViewModel";

    static final int SUCCESS = 0;
    static final int FAILURE = 1;
    int flag=0;

    Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case SUCCESS:
                    flag=1;
                    break;
                case FAILURE:
                    break;
            }
        }
    };

    public DashboardViewModel() throws InterruptedException {

        Log.d(TAG, "DashboardViewModel: 首先执行");
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        userlist = new MutableLiveData<>();
        userlist.setValue("100000");

        shareModellist=new ArrayList<>();
        //newsList=new MutableLiveData<>();

        Log.d(TAG, "开始");
        getShareDate();
//        newsList.setValue(shareModellist);
        Log.d(TAG, "结束"+shareModellist);

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getNumber(){
        return userlist;
    }

    public LiveData<List<RecordsBean>> getArryList(){
        return newsList;
    }

    public  void getShareDate()  {
        ShareService shareService= RetrofitUtils.getInstance().getRetrofit().create(ShareService.class);
        Call<ShareModel> call=shareService.sharedate(0,10,0);

        call.enqueue(new Callback<ShareModel>() {
            @Override
            public void onResponse(Call<ShareModel> call, Response<ShareModel> response) {
                ShareModel shareModel = response.body();
                shareModellist.addAll(shareModel.getData().getRecords());
                Log.d(TAG, "sss"+shareModellist);
                newsList.setValue(shareModellist);
                handler.sendEmptyMessage(SUCCESS);
            }

            @Override
            public void onFailure(Call<ShareModel> call, Throwable t) {
                    t.printStackTrace();
                    handler.sendEmptyMessage(FAILURE);
            }
        });
    }
}