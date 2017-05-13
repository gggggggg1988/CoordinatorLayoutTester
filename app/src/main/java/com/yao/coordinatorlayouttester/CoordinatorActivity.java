package com.yao.coordinatorlayouttester;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorActivity extends AppCompatActivity {

    @BindView(R.id.rl_tab_left)
    RelativeLayout rlTabLeft;
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

        rlTabLeft.setSelected(true);





    }

}
