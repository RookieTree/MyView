package com.tree.myview.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tree.myview.R;

import androidx.annotation.NonNull;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.adapter
 *  @文件名:   BigBitmapAdapter
 *  @创建者:   rookietree
 *  @创建时间:  2022/3/22 10:48
 *  @描述：    TODO
 */
public class BigBitmapAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public BigBitmapAdapter() {
        super(R.layout.item_bitmap);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        int i=Integer.parseInt(s);
        if (i%5==0){
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.meinv5);
        }else if (i%5==1){
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.meinv6);
        }else if (i%5==2){
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.meinv10);
        }else if (i%5==3){
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.meinv11);
        }else if (i%5==4){
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.meinv12);
        }

    }
}
