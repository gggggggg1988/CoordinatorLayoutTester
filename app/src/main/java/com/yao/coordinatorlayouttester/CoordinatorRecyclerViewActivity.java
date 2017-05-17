package com.yao.coordinatorlayouttester;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rl_tab_left)
    RelativeLayout mRlTabLeft;
    @BindView(R.id.rl_tab_right)
    RelativeLayout mRlTabRight;
    @BindView(R.id.title_bar)
    View titleBar;
    @BindView(R.id.header_content)
    TextView mTvHeader;
    @BindView(R.id.middle_content)
    View middleContent;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.recycler_view_left)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.recycler_view_right)
    RecyclerView mRecyclerViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_recyclerview);
        ButterKnife.bind(this);
        mRecyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewLeft.setAdapter(new NormalRecyclerViewAdapter(this, getResources().getStringArray(R.array.titles)));
        mRecyclerViewLeft.setNestedScrollingEnabled(false);


        mRecyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewRight.setAdapter(new NormalRecyclerViewAdapter(this, getResources().getStringArray(R.array.province)));
        mRecyclerViewRight.setNestedScrollingEnabled(false);

        mRlTabLeft.setSelected(true);
        mRecyclerViewLeft.setVisibility(View.VISIBLE);
        mRecyclerViewRight.setVisibility(View.GONE);
        mRlTabLeft.setOnClickListener(this);
        mRlTabRight.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_tab_left:
                if (!mRlTabLeft.isSelected()) {
                    mRlTabLeft.setSelected(true);
                    mRlTabRight.setSelected(false);
                    mRecyclerViewLeft.setVisibility(View.VISIBLE);
                    mRecyclerViewRight.setVisibility(View.GONE);
                    nestedScrollView.smoothScrollTo(0, middleContent.getTop());
                }
                break;
            case R.id.rl_tab_right:
                if (!mRlTabRight.isSelected()) {
                    mRlTabRight.setSelected(true);
                    mRlTabLeft.setSelected(false);
                    mRecyclerViewRight.setVisibility(View.VISIBLE);
                    mRecyclerViewLeft.setVisibility(View.GONE);
                    Log.e("YAO", "CoordinatorRecyclerViewActivity.java - onClick() ---------- middleContent.getTop();:" + middleContent.getTop() );
                    Log.e("YAO", "CoordinatorRecyclerViewActivity.java - onClick() ---------- titleBar.getHeight():" + titleBar.getHeight() );
                    nestedScrollView.smoothScrollTo(0, middleContent.getTop());
                }
                break;
        }
    }
}
