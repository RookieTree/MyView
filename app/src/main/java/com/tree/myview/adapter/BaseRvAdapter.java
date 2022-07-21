package com.tree.myview.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tree.myview.R;

import androidx.annotation.NonNull;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.adapter
 *  @文件名:   BaseRvAdapter
 *  @创建者:   rookietree
 *  @创建时间:  2022/2/16 18:15
 *  @描述：    TODO
 */
public class BaseRvAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public BaseRvAdapter() {
        super(R.layout.item_scroll);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv,s);
    }
}
