package com.company.weathervietnamongooglemap.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.company.weathervietnamongooglemap.R;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initComponents();
        initData();
    }

    public void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void initComponents();

    protected abstract void initData();
}
