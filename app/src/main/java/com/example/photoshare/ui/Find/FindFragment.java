package com.example.photoshare.ui.Find;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.photoshare.activity.ShareActivity;
import com.example.photoshare.adapter.NewsAdapter;
import com.example.photoshare.databinding.FragmentDashboardBinding;
import com.example.photoshare.model.share.RecordsBean;

import java.util.List;

public class FindFragment extends Fragment {

    private FragmentDashboardBinding binding;
    NewsAdapter newsAdapter;

    //在需要刷新的地方添加广播
    LocalBroadcastManager broadcastManager;

    FindViewModel findViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sh=getActivity().getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");

        findViewModel =
                new ViewModelProvider(this,new FindViewModelFactory(user_id)).get(FindViewModel.class);
        //使fragment与ViewModel绑定
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取根视图

        registerReceiver();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        newsAdapter=new NewsAdapter(getContext());
        binding.recyclerView.setAdapter(newsAdapter);
        findViewModel.getArryList().observe(getViewLifecycleOwner(), new Observer<List<RecordsBean>>() {
            @Override
            public void onChanged(List<RecordsBean> shares) {
                newsAdapter.setSharelist(shares);
                newsAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("zachary");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String refresh= intent.getStringExtra("refreshInfo");
            if ("yes".equals(refresh)) {
                // 在主线程中刷新UI，用Handler来实现

                findViewModel.getShareDate();
                findViewModel.getArryList().observe(getViewLifecycleOwner(), new Observer<List<RecordsBean>>() {
                    @Override
                    public void onChanged(List<RecordsBean> shares) {
                        newsAdapter.setSharelist(shares);
                        newsAdapter.notifyDataSetChanged();
                    }
                });

                Toast.makeText(context, "广播成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    //注销广播
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}