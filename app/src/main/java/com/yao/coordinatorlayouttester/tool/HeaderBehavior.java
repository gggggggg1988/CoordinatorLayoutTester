package com.yao.coordinatorlayouttester.tool;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private TabLayout mTabLayout;
    private FrameLayout mSpace;
    private LinearLayout llLocation;
    private ImageView mIvShoppingCart;

//    private int titleHeight;
    private int headerContentHeight;
    private int llLocationHeight;

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
            mTabLayout = (TabLayout) parent.findViewById(R.id.tab_layout);
            mSpace = (FrameLayout) parent.findViewById(R.id.space);
            llLocation = (LinearLayout) parent.findViewById(R.id.ll_location);
            mIvShoppingCart = (ImageView) parent.findViewById(R.id.iv_shopping_cart);

            llLocationHeight = llLocation.getMeasuredHeight();
            headerContentHeight = mHeaderContent.getMeasuredHeight();

            mIvShoppingCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "HeaderBehavior.java - onClick() ---------- mMiddleContent:" + (mMiddleContent.getChildCount()>0 ? mMiddleContent.getChildAt(0) : "empty"));
                    Log.e(TAG, "HeaderBehavior.java - onClick() ---------- mSpace:" + (mSpace.getChildCount()>0 ? mSpace.getChildAt(0) : "empty"));
                }
            });

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMiddleContent.getLayoutParams();
            params.height = mTabLayout.getHeight();
            mMiddleContent.setLayoutParams(params);
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
                    mMiddleContent.addView(mTabLayout);
                }
                status = STATUS_TOP;

                Log.e(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 前期");
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                llLocation.setLayoutParams(params);

                //调整透明度
                llLocation.setBackgroundColor(Color.WHITE);
                mSpace.setBackgroundColor(Color.WHITE);


            }
        } else if (target.getScrollY() + mTitleBar.getBottom() > mHeaderContent.getBottom() && target.getScrollY() < mHeaderContent.getBottom()) {
            if (status != STATUS_TRANSFORM) {
                if (status == STATUS_BOTTOM) {
                    mSpace.removeAllViews();
                    mMiddleContent.addView(mTabLayout);
                }
                status = STATUS_TRANSFORM;
                Log.e(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 中期");
                //调整透明度
                llLocation.setBackgroundColor(Color.TRANSPARENT);
                mSpace.setBackgroundColor(Color.TRANSPARENT);
            }

            //平移到屏幕左侧
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
            //当target.getScrollY() + mTitleBar.getBottom() = mHeaderContent.getBottom()， llLocation的marginLeft为0
            //当target.getScrollY() = mTitleBar.getBottom()， llLocation的marginLeft为-llLocation.getWidth()
            Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- " + llLocation.getWidth() * (headerContentHeight - target.getScrollY() - mTitleBar.getBottom()));
            float ratio = (mHeaderContent.getBottom() - (target.getScrollY() + mTitleBar.getBottom())) * 1.0f / mTitleBar.getBottom() ;
            Log.d(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- ratio:" + ratio);
            params.setMargins((int) (llLocation.getWidth() * ratio), 0, 0, 0);
            llLocation.setLayoutParams(params);
        } else if (target.getScrollY() > mHeaderContent.getBottom()) {
//            mSpace.addView(mTabLayout);
            if (status != STATUS_BOTTOM) {
                status = STATUS_BOTTOM;
                Log.e(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- 后期");

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llLocation.getLayoutParams();
                params.setMargins(llLocation.getWidth() * -1, 0, 0, 0);
                llLocation.setLayoutParams(params);

                mMiddleContent.removeAllViews();
                Log.e(TAG, "HeaderBehavior.java - onNestedPreScroll() ---------- " + mTabLayout.getParent());
                mSpace.addView(mTabLayout);

                llLocation.setBackgroundColor(Color.WHITE);
                mSpace.setBackgroundColor(Color.WHITE);
            }



        }

    }
}
