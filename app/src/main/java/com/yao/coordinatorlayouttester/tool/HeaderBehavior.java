package com.yao.coordinatorlayouttester.tool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yao.coordinatorlayouttester.R;

/**
 * Created by Administrator on 2017/5/6.
 */

public class HeaderBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public static final int STATUS_TOP = 1;
    public static final int STATUS_TRANSFORM = 2;
    public static final int STATUS_BOTTOM = 3;


    private static final String TAG = "HeaderBehavior";

    private Context mContext;
    private boolean isFirst = true;
    private View mTitleBar;
    private View mHeaderContent;
    private ViewGroup mMiddleContent;
    private LinearLayout mLlTabBar;
    private FrameLayout mSpace;
    private LinearLayout llLocation;
    private ImageView mIvShoppingCart;

    private RelativeLayout mRlTabLeft;
    private RelativeLayout mRlTabRight;
    private TextView mTvTitleLeft;
    private TextView mTvTitleRight;

//    private int titleHeight;
    private int headerContentHeight;
    private int llLocationHeight;

    private Paint mTextPaint;
    private int titleLeftWidth;
    private int titleRightWidth;
    private int shortRlTabLeft;
    private int shortRlTabRight;
    private int longRlTabLeft;
    private int longRlTabRight;
    private int titleLeft_leftMargin;

    private int status = 0;

    public HeaderBehavior() {
        init();
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        Log.d(TAG, "HeaderBehavior.java - init() ---------- ");
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        Log.d(TAG, "HeaderBehavior.java - layoutDependsOn() ---------- dependency:" + dependency.getClass().getSimpleName() + "  child:" + child.getClass().getSimpleName() + ":" + child.getId());
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        Log.d(TAG, "HeaderBehavior.java - onDependentViewChanged() ---------- ");
        if (isFirst == true) {
            isFirst = false;
            mTitleBar = parent.findViewById(R.id.title_bar);
            mHeaderContent = parent.findViewById(R.id.header_content);
            mMiddleContent = (ViewGroup) parent.findViewById(R.id.middle_content);
            mLlTabBar = (LinearLayout) parent.findViewById(R.id.ll_tab_bar);
            mSpace = (FrameLayout) parent.findViewById(R.id.space);
            llLocation = (LinearLayout) parent.findViewById(R.id.ll_location);
            mIvShoppingCart = (ImageView) parent.findViewById(R.id.iv_shopping_cart);
            mRlTabLeft = (RelativeLayout) parent.findViewById(R.id.rl_tab_left);
            mRlTabRight = (RelativeLayout) parent.findViewById(R.id.rl_tab_right);
            mTvTitleLeft = (TextView) parent.findViewById(R.id.tv_title_left);
            mTvTitleRight = (TextView) parent.findViewById(R.id.tv_title_right);

            llLocationHeight = llLocation.getMeasuredHeight();
            headerContentHeight = mHeaderContent.getMeasuredHeight();

            mIvShoppingCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "HeaderBehavior.java - onClick() ---------- mMiddleContent:" + (mMiddleContent.getChildCount()>0 ? mMiddleContent.getChildAt(0) : "empty"));
                    Log.e(TAG, "HeaderBehavior.java - onClick() ---------- mSpace:" + (mSpace.getChildCount()>0 ? mSpace.getChildAt(0) : "empty"));
                }
            });

            //给MiddleContent设置跟标题栏一样的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMiddleContent.getLayoutParams();
            params.height = mLlTabBar.getHeight();
            mMiddleContent.setLayoutParams(params);

            //创建画笔
            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            //根据tvTitleLeft的文字，获取tvTitleLeft纯文字宽度
            mTextPaint.setTextSize(mTvTitleLeft.getTextSize());
            titleLeftWidth = (int) mTextPaint.measureText(mTvTitleLeft.getText().toString());
            //根据tvTitleRight的文字，根据tvTitleRight的文字
            mTextPaint.setTextSize(mTvTitleRight.getTextSize());
            titleRightWidth = (int) mTextPaint.measureText(mTvTitleRight.getText().toString());

            //得到两个tab的原始宽度
            longRlTabLeft = mRlTabLeft.getWidth();
            longRlTabRight = mRlTabRight.getWidth();

            //修改leftTab的宽度从weight=1的百分比方式到真是宽度方式
            LinearLayout.LayoutParams tabLeftParams = (LinearLayout.LayoutParams) mRlTabLeft.getLayoutParams();
            tabLeftParams.width = longRlTabLeft;
            tabLeftParams.weight = 0;
            mRlTabLeft.setLayoutParams(tabLeftParams);

            //修改rightTab的宽度从weight=1的百分比方式到真是宽度方式
            LinearLayout.LayoutParams tabRightParams = (LinearLayout.LayoutParams) mRlTabRight.getLayoutParams();
            tabRightParams.width = longRlTabRight;
            tabRightParams.weight = 0;
            mRlTabRight.setLayoutParams(tabRightParams);

            RelativeLayout.LayoutParams titleLeftParams = (RelativeLayout.LayoutParams) mTvTitleLeft.getLayoutParams();
            titleLeft_leftMargin = titleLeftParams.leftMargin;



            Log.e(TAG, "HeaderBehavior.java - onDependentViewChanged() ---------- titleLeftWidth:" + titleLeftWidth );
            Log.e(TAG, "HeaderBehavior.java - onDependentViewChanged() ---------- titleRightWidth:" + titleRightWidth );

        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, "HeaderBehavior.java - onStartNestedScroll() ---------- child:" + child.getClass().getSimpleName()
                + "  directTargetChild:" + directTargetChild.getClass().getSimpleName()
                + "  target:" + target.getClass().getSimpleName() + "  nestedScrollAxes:" + nestedScrollAxes );
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, final View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- child:" + child.getClass().getSimpleName()
                + "   target:" + target.getClass().getSimpleName()
                + "   dx:" + dx + "   dy:" + dy
                + "   consumed[0]:" + consumed[0] + "   consued[1]:" + consumed[1]);
        Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- target.getScrollY()：" + target.getScrollY() + "   headerContentHeight:" + headerContentHeight);
        if (target.getScrollY() + mTitleBar.getBottom() < mHeaderContent.getBottom()) {
            //滑的太快会是llLocation显示不完全，所以需要这里复位它的位置
            if (status != STATUS_TOP) {
                if (status == STATUS_BOTTOM) {
                    mSpace.removeAllViews();
                    mMiddleContent.addView(mLlTabBar);
                }
                status = STATUS_TOP;

                Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 前期");

                //location位置回复
                LinearLayout.LayoutParams locationParams = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
                locationParams.setMargins(0, 0, 0, 0);
                llLocation.setLayoutParams(locationParams);

                //leftTab宽度回复
                LinearLayout.LayoutParams tabLeftParams = (LinearLayout.LayoutParams) mRlTabLeft.getLayoutParams();
                tabLeftParams.width = longRlTabLeft;
                mRlTabLeft.setLayoutParams(tabLeftParams);

                //rightTab宽度回复
                LinearLayout.LayoutParams tabRightParams = (LinearLayout.LayoutParams) mRlTabRight.getLayoutParams();
                tabLeftParams.width = longRlTabRight;
                mRlTabRight.setLayoutParams(tabRightParams);

                //titleLeft的marginleft回复
                RelativeLayout.LayoutParams titleLeftParams = (RelativeLayout.LayoutParams) mTvTitleLeft.getLayoutParams();
                titleLeftParams.setMargins(titleLeft_leftMargin, 0, 0, 0);

                //调整透明度
                llLocation.setBackgroundColor(Color.WHITE);
                mSpace.setBackgroundColor(Color.WHITE);


            }
        } else if (target.getScrollY() + mTitleBar.getBottom() > mHeaderContent.getBottom() && target.getScrollY() < mHeaderContent.getBottom()) {
            if (status != STATUS_TRANSFORM) {
                if (status == STATUS_BOTTOM) {
                    mSpace.removeAllViews();
                    mMiddleContent.addView(mLlTabBar);
                }
                status = STATUS_TRANSFORM;
                Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 中期");
                //调整透明度
                llLocation.setBackgroundColor(Color.TRANSPARENT);
                mSpace.setBackgroundColor(Color.TRANSPARENT);
            }



            //上滑到下，比率由0到1的过程。下滑到上，比率由1到0。
            Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- " + llLocation.getWidth() * (headerContentHeight - target.getScrollY() - mTitleBar.getBottom()));
            float ratio = (target.getScrollY() + mTitleBar.getBottom() - mHeaderContent.getBottom()) * 1.0f / mTitleBar.getBottom() ;
            Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- ratio:" + ratio);


            //location平移到屏幕左侧
            //当target.getScrollY() + mTitleBar.getBottom() = mHeaderContent.getBottom()， llLocation的marginLeft为0
            //当target.getScrollY() = mTitleBar.getBottom()， llLocation的marginLeft为-llLocation.getWidth()
            LinearLayout.LayoutParams locationParams = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
            locationParams.setMargins((int) (llLocation.getWidth() * -ratio), 0, 0, 0);
            llLocation.setLayoutParams(locationParams);
            
            //leftTab宽度变小
            LinearLayout.LayoutParams tabLeftParams = (LinearLayout.LayoutParams) mRlTabLeft.getLayoutParams();
            tabLeftParams.width = (int) (titleLeftWidth + (longRlTabLeft - titleLeftWidth) * (1-ratio));
            mRlTabLeft.setLayoutParams(tabLeftParams);

            //rightTab宽度变小
            LinearLayout.LayoutParams tabRightParams = (LinearLayout.LayoutParams) mRlTabRight.getLayoutParams();
            tabRightParams.width = (int) (titleRightWidth + (longRlTabRight - titleRightWidth) * (1-ratio));
            mRlTabLeft.setLayoutParams(tabRightParams);

            //titleLeft的marginleft逐渐变小
            int detal = (int) ((longRlTabRight - titleRightWidth) * (ratio));
            RelativeLayout.LayoutParams titleLeftParams = (RelativeLayout.LayoutParams) mTvTitleLeft.getLayoutParams();
            titleLeftParams.setMargins(detal>titleLeft_leftMargin ? 0 : titleLeft_leftMargin - detal, 0, 0, 0);
        } else if (target.getScrollY() > mHeaderContent.getBottom()) {
//            mSpace.addView(mTabLayout);
            if (status != STATUS_BOTTOM) {
                status = STATUS_BOTTOM;
                Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 后期");

                //location位置左移到不见
                LinearLayout.LayoutParams locationParams = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
                locationParams.setMargins(llLocation.getWidth() * -1, 0, 0, 0);
                llLocation.setLayoutParams(locationParams);

                //leftTab宽度缩小
                LinearLayout.LayoutParams tabLeftParams = (LinearLayout.LayoutParams) mRlTabLeft.getLayoutParams();
                tabLeftParams.width = titleLeftWidth;
                mRlTabLeft.setLayoutParams(tabLeftParams);

                //rightTab宽度缩小
                LinearLayout.LayoutParams tabRightParams = (LinearLayout.LayoutParams) mRlTabRight.getLayoutParams();
                tabLeftParams.width = titleRightWidth;
                mRlTabRight.setLayoutParams(tabRightParams);

                //titleLeft的marginleft变成0
                RelativeLayout.LayoutParams titleLeftParams = (RelativeLayout.LayoutParams) mTvTitleLeft.getLayoutParams();
                titleLeftParams.setMargins(0, 0, 0, 0);




                mMiddleContent.removeAllViews();
                mSpace.addView(mLlTabBar);

                llLocation.setBackgroundColor(Color.WHITE);
                mSpace.setBackgroundColor(Color.WHITE);
            }
        }

    }
}
