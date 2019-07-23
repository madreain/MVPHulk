package com.madreain.mvphulk.module.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.madreain.hulk.ui.BaseActivity;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.module.Home.HomeFragment;
import com.madreain.mvphulk.module.Hulk.HulkFragment;
import com.madreain.mvphulk.module.My.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author madreain
 * @date 2019-07-15.
 * module：
 * description：
 */

@Route(path = ARouterUri.MainActivity)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    List<Fragment> fragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HulkFragment());
        fragmentList.add(new MyFragment());
        navigation.setOnNavigationItemSelectedListener(this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    @Override
    public View getReplaceView() {
        return viewPager;
    }

    @OnClick({R.id.viewpager, R.id.navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.viewpager:
                break;
            case R.id.navigation:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_hulk:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_my:
                viewPager.setCurrentItem(2);
                return true;
            default:
                break;
        }
        return false;
    }

}
