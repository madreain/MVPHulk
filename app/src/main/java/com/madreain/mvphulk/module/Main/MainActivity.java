package com.madreain.mvphulk.module.Main;

import android.os.Bundle;
import android.support.annotation.IdRes;
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

    private enum TabFragment {
        home(R.id.navigation_home, HomeFragment.class),
        hulk(R.id.navigation_hulk, HulkFragment.class),
        my(R.id.navigation_my, MyFragment.class),
        ;

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId) {
                    return fragment;
                }
            }
            return home;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }

    }

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(this);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return TabFragment.values().length;
            }

            @Override
            public Fragment getItem(int position) {
                return TabFragment.values()[position].fragment();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(TabFragment.values()[position].menuId);
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
        ((ViewPager) findViewById(R.id.viewpager)).setCurrentItem(TabFragment.from(menuItem.getItemId()).ordinal());
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }
}
