package com.example.photoshare.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoshare.adapter.NewsAdapter;
import com.example.photoshare.databinding.FragmentDashboardBinding;
import com.example.photoshare.model.share.RecordsBean;
import com.example.photoshare.model.share.ShareModel;
import com.example.photoshare.ui.notifications.NotificationsViewModelFactory;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sh=getActivity().getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this,new DashboardViewModelFactory(user_id)).get(DashboardViewModel.class);
        //使fragment与ViewModel绑定
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取根视图

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        NewsAdapter newsAdapter=new NewsAdapter(getContext());
        binding.recyclerView.setAdapter(newsAdapter);
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