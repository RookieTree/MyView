package com.tree.myview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tree.myview.R;
import com.tree.myview.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.myview.view
 *  @文件名:   ChoukaView
 *  @创建者:   rookietree
 *  @创建时间:  2021/10/11 3:33 下午
 *  @描述：    TODO
 */
public class ChoukaView extends ConstraintLayout {

    private Context mContext;
    private ImageView mCard;
    private ImageView mCard1;
    private ImageView mCard2;
    private ImageView mCard3;
    private ImageView mCard4;
    private ImageView mCard5;
    private ImageView mCard6;
    private ImageView mCard7;
    private ImageView mCard8;
    private ImageView mCard9;
    private ImageView mCard10;
    private int[][] translates = {{0, -197}, {0, -62}, {0, 197}, {0, 62},
            {-85, -135}, {-85, 0}, {-85, 135},
            {85, -135}, {85, 0}, {85, 135},};
    private ImageView[] imgs;


    public ChoukaView(Context context) {
        this(context, null);
    }

    public ChoukaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoukaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View inflate = layoutInflater.inflate(R.layout.view_chouka, this);
        mCard = inflate.findViewById(R.id.card);
        mCard1 = inflate.findViewById(R.id.card1);
        mCard2 = inflate.findViewById(R.id.card2);
        mCard3 = inflate.findViewById(R.id.card3);
        mCard4 = inflate.findViewById(R.id.card4);
        mCard5 = inflate.findViewById(R.id.card5);
        mCard6 = inflate.findViewById(R.id.card6);
        mCard7 = inflate.findViewById(R.id.card7);
        mCard8 = inflate.findViewById(R.id.card8);
        mCard9 = inflate.findViewById(R.id.card9);
        mCard10 = inflate.findViewById(R.id.card10);

        imgs = new ImageView[]{mCard1, mCard2, mCard3, mCard4, mCard5, mCard6, mCard7, mCard8, mCard9, mCard10};

        mCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playtogether();
            }
        });
    }

    public void playtogether() {
        AnimatorSet animatorCards = new AnimatorSet();
        animatorCards.setDuration(1000);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mCard, "scaleX", 1, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mCard, "scaleY", 1, 0.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mCard, "alpha", 1, 0);
        AnimatorSet animatorCard = new AnimatorSet();
        animatorCard.playTogether(scaleX, scaleY, alpha);
        animatorCard.setDuration(1000);
        List<Animator> list = new ArrayList<>();
        for (int i = 0; i < translates.length; i++) {
            int[] nums = translates[i];
            Animator[] animators = tanslateCard(imgs[i], nums[0], nums[1]);
            list.add(animators[0]);
            list.add(animators[1]);
            list.add(animators[2]);
        }
        animatorCard.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCard.setVisibility(GONE);
                for (ImageView iv : imgs) {
                    iv.setVisibility(VISIBLE);
                }
                animatorCards.playTogether(list);
                animatorCards.start();
            }
        });
        animatorCard.start();
    }

    public Animator[] tanslateCard(ImageView iv, float transX, float transY) {
        ObjectAnimator translateX = ObjectAnimator.ofFloat(iv, "translationX", 0,
                CommonUtils.dip2px(mContext, transX));
        ObjectAnimator translateY = ObjectAnimator.ofFloat(iv, "translationY", 0,
                CommonUtils.dip2px(mContext, transY));
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0, 1);
        return new Animator[]{translateX, translateY, alpha};
    }
}
