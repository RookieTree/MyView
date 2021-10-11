package com.tree.myview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.fragment
 *  @文件名:   BaseFragment
 *  @创建者:   rookietree
 *  @创建时间:  4/27/21 3:00 PM
 *  @描述：    TODO
 */
public abstract class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public abstract int getLayoutId();


    protected abstract void init(View view);

}
