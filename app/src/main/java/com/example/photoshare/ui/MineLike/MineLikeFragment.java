package com.example.photoshare.ui.MineLike;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.photoshare.activity.GuanZhuActivity;
import com.example.photoshare.activity.ShouCangActivity;
import com.example.photoshare.adapter.MineLikeAdapter;
import com.example.photoshare.databinding.FragmentHomeBinding;

import java.util.List;

public class MineLikeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sh=getActivity().getSharedPreferences("user",0);
        String user_id = sh.getString("id","未找到用户ID");

        MineLikeViewModel mineLikeViewModel =
                new ViewModelProvider(this,new MineLikeViewModelFactoyr(user_id)).get(MineLikeViewModel.class);
        //使fragment与ViewModel绑定，数据获取与页面处理结合
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //获取根视图

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        MineLikeAdapter mineLikeAdapter=new MineLikeAdapter(getContext());
        binding.recyclerView.setAdapter(mineLikeAdapter);
        mineLikeViewModel.getArryList().observe(getViewLifecycleOwner(), new Observer<List<com.example.photoshare.model.minelike.RecordsBean>>() {
            @Override
            public void onChanged(List<com.example.photoshare.model.minelike.RecordsBean> recordsBeans) {
                mineLikeAdapter.setSharelist2(recordsBeans);
                mineLikeAdapter.notifyDataSetChanged();
            }
        });

        binding.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ShouCangActivity.class);
                startActivity(intent);
            }
        });

        binding.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), GuanZhuActivity.class);
                startActivity(intent);
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