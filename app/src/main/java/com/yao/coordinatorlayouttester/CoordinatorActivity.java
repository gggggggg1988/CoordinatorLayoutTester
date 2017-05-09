package com.yao.coordinatorlayouttester;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.header_content)
    TextView tvHeader;
    @BindView(R.id.tv_content_1)
    TextView tvContent1;
    @BindView(R.id.tv_content_2)
    TextView tvContent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText("附近商家(1024家）"));
        tabLayout.addTab(tabLayout.newTab().setText("此刻最热"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Snackbar.make(getWindow().getDecorView(), "标签 " + tab.getPosition() + " 被点击了", Snackbar.LENGTH_SHORT).show();
                if (tab.getPosition() == 0) {
                    tvContent1.setVisibility(View.VISIBLE);
                    tvContent2.setVisibility(View.GONE);
                } else {
                    tvContent2.setVisibility(View.VISIBLE);
                    tvContent1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

}
