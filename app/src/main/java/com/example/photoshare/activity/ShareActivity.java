package com.example.photoshare.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import com.example.photoshare.R;
import com.example.photoshare.ui.notifications.NotificationsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.photoshare.databinding.ActivityShareBinding;
import com.google.android.material.navigation.NavigationView;

public class ShareActivity extends AppCompatActivity {

    private ActivityShareBinding binding;

    private Toolbar tool_bar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareBinding.inflate(getLayoutInflater());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard,R.id.navigation_home, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_share);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        init();
        listen();


    }

    private void init() {
        //绑定 NavigationView控件
        navView = findViewById(R.id.navView);
        //绑定 DrawerLayout控件
        drawerLayout = findViewById(R.id.drawer_layout);
        //绑定 Toolbar控件
        tool_bar = findViewById(R.id.tool_bar);
        //得到toolBar实例
        setSupportActionBar(tool_bar);
        //显示 tool_bar左侧按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置 tool_bar左侧按钮图标
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp);
        //设置 note_group为默认选中
        navView.setCheckedItem(R.id.note_group);
    }

    private void listen() {
        //设置 一个菜单选项 选中事件 的监听器
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //显示 当前点击了哪个item
                switch (item.getItemId()) {
                    case R.id.note_group:
                        Toast.makeText(getApplication(), "你点击了note_group", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.note_collect:
                        Toast.makeText(getApplication(), "你点击了note_collect", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.note_setting:
                        Toast.makeText(getApplication(), "你点击了note_setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                //关闭 滑动菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    //加载 toorlbar.xml这个菜单文件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    //处理多个按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //按下home按钮 打开滑动菜单
                //openDrawer() 传入gravity参数 确保这里的行为和xml中定义的一致
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.note_group:
                Toast.makeText(this, "you clicked love", Toast.LENGTH_SHORT).show();
                break;
            case R.id.note_collect:
                Toast.makeText(this, "you clicked save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.note_setting:
                Toast.makeText(this, "you clicked rewrite", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}