package com.unipock.unipaytool.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.unipock.unipaytool.R;


public class TitleBar extends Toolbar {

    // 中间标题，图标
    private TextView mCenterTitle;
    private ImageView mCenterIcon;

    //左侧标题，图标
    private TextView mLeftTitle;
    private ImageView mLeftIcon;

    //右侧标题，图标
    private TextView mRightTitle;
    private ImageView mRightIcon;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCenterTitle(String centerTitle) {
        Context context = this.getContext();
        if (this.mCenterTitle == null) {
            this.mCenterTitle = new TextView(context);
            this.mCenterTitle.setGravity(Gravity.CENTER);
            this.mCenterTitle.setSingleLine(true);
            this.mCenterTitle.setEllipsize(TextUtils.TruncateAt.END);
            this.mCenterTitle.setTextSize(16);
            // 添加样式
            this.mCenterTitle.setTextAppearance(context, R.style.title_bar_text);
            addMyView(this.mCenterTitle, Gravity.CENTER);
        } else {
            if (this.mCenterTitle.getVisibility() != VISIBLE) {
                mCenterTitle.setVisibility(VISIBLE);
            }
        }
        // 把中间的图标隐藏
        if (this.mCenterIcon != null && this.mCenterIcon.getVisibility() != GONE) {
            this.mCenterIcon.setVisibility(GONE);
        }
        this.setTitle("");
        this.mCenterTitle.setText(centerTitle);
    }
// tool sdk
    public TextView getmCenterTitle() {
        return mCenterTitle;
    }

    public void setCenterIcon(int resCenterId) {

        Context context = getContext();
        if (this.mCenterIcon == null) {
            this.mCenterIcon = new ImageView(context);
            this.mCenterIcon.setScaleType(ImageView.ScaleType.CENTER);
            addMyView(mCenterIcon, Gravity.CENTER);
        } else {
            if (this.mCenterIcon.getVisibility() != VISIBLE) {
                this.mCenterIcon.setVisibility(VISIBLE);
            }
        }
        if (this.mCenterTitle != null && this.mCenterTitle.getVisibility() != GONE) {
            this.mCenterTitle.setVisibility(GONE);
        }
        mCenterIcon.setImageDrawable(ContextCompat.getDrawable(this.getContext(), resCenterId));
    }


    public void setLeftTitle(String leftTitle) {

        Context context = getContext();
        if (this.mLeftTitle == null) {
            this.mLeftTitle = new TextView(context);
            this.mLeftTitle.setSingleLine();
            this.mLeftTitle.setEllipsize(TextUtils.TruncateAt.END);
            this.mLeftTitle.setGravity(Gravity.CENTER_VERTICAL);
            this.mLeftTitle.setTextAppearance(context, R.style.title_bar_text);
            this.addMyView(mLeftTitle, Gravity.START);
        }
        mLeftTitle.setText(leftTitle);
    }


    public void setLeftIcon(int leftIconId) {

        Context context = getContext();
        if (this.mLeftIcon == null) {
            this.mLeftIcon = new ImageView(context);
            this.mLeftIcon.setScaleType(ImageView.ScaleType.CENTER);
            addMyView(mLeftIcon, Gravity.START);
        } else {
            if (this.mLeftIcon.getVisibility() != VISIBLE) {
                this.mLeftIcon.setVisibility(VISIBLE);
            }
        }
        mLeftIcon.setImageDrawable(ContextCompat.getDrawable(this.getContext(), leftIconId));

    }

    public void setRightTitle(String rightTitle) {
        Context context = getContext();
        if (this.mRightTitle == null) {
            this.mRightTitle = new TextView(context);
            this.mRightTitle.setSingleLine();
            this.mRightTitle.setEllipsize(TextUtils.TruncateAt.END);
            this.mRightTitle.setGravity(Gravity.CENTER_VERTICAL);
            this.mRightTitle.setTextAppearance(context, R.style.title_bar_text);
            this.addMyView(mRightTitle, Gravity.END);
        }
        mRightTitle.setText(rightTitle);
    }

    public void setRightIcon(int rightIconId) {
        Context context = getContext();
        if (this.mRightIcon == null) {
            this.mRightIcon = new ImageView(context);
            this.mRightIcon.setScaleType(ImageView.ScaleType.CENTER);
            addMyView(mRightIcon, Gravity.END);
        } else {
            if (this.mRightIcon.getVisibility() != VISIBLE) {
                this.mRightIcon.setVisibility(VISIBLE);
            }
        }
        mRightIcon.setImageDrawable(ContextCompat.getDrawable(this.getContext(), rightIconId));
    }

    public void setLeftOnClickListener(OnClickListener onClickListener) {
        if (mLeftTitle != null) {
            mLeftTitle.setOnClickListener(onClickListener);
        }
        if (mLeftIcon != null) {
            mLeftIcon.setOnClickListener(onClickListener);
        }
    }

    public void setCenterOnClickListener(OnClickListener onClickListener) {
        if (mCenterTitle != null) {
            mCenterTitle.setOnClickListener(onClickListener);
        }
        if (mCenterIcon != null) {
            mCenterIcon.setOnClickListener(onClickListener);
        }
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {
        if (mRightTitle != null) {
            mRightTitle.setOnClickListener(onClickListener);
        }
        if (mRightIcon != null) {
            mRightIcon.setOnClickListener(onClickListener);
        }
    }

    private void addMyView(View v, int gravity) {
        addMyView(v, gravity, 0, 0, 0, 0);
    }

    private void addMyView(View v, int gravity, int left, int top, int right, int bottom) {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, gravity);
        lp.setMargins(left, top, right, bottom);
        this.addView(v, lp);
    }


}
