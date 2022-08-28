package com.example.photoshare.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoshare.adapter.NewsAdapter;
import com.example.photoshare.databinding.FragmentDashboardBinding;
import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.share.ShareModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        //使fragment与ViewModel绑定
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取根视图

        final TextView textView = binding.textDashboard;
        final TextView textView1= binding.textDashboard2;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        dashboardViewModel.getNumber().observe(getViewLifecycleOwner(),textView1::setText);
        //这里使用了ViewModel中的get方法，返回的是一个生命周期感知组件，observe是内容观察者
        //观察者模式

        final RecyclerView recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NewsAdapter newsAdapter=new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);
        dashboardViewModel.getArryList().observe(getViewLifecycleOwner(), new Observer<List<RecordsBean>>() {
            @Override
            public void onChanged(List<RecordsBean> shares) {
                newsAdapter.setSharelist(shares);
                newsAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}