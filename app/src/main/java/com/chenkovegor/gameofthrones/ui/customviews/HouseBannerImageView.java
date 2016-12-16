package com.chenkovegor.gameofthrones.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HouseBannerImageView extends ImageView {

    public HouseBannerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWidth;
        int newHeight;

        newWidth = getMeasuredWidth();
        newHeight = (int) (newWidth * 1.5);

        setMeasuredDimension(newWidth, newHeight);
    }
}
