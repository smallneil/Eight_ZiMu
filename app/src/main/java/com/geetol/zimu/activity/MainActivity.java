package com.geetol.zimu.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.shichai.zimu.R;
import com.geetol.zimu.base.BaseActivity;
import com.geetol.zimu.bean.BannerData;
import com.geetol.zimu.fragment.UserFragment;
import com.geetol.zimu.utils.DisplayUtil;
import com.geetol.zimu.utils.ScaleTransformer;
import com.geetol.zimu.widget.CustomViewHolder;
import com.geetol.zimu.widget.HeadLayout;
import com.geetol.zimu.widget.ItemFunctionLayoutOne;
import com.geetol.zimu.widget.ItemFunctionLayoutTwo;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.add_subtitles)
    ItemFunctionLayoutOne addSubtitles;
    @BindView(R.id.video_clip)
    ItemFunctionLayoutOne videoClip;
    @BindView(R.id.add_watermark)
    ItemFunctionLayoutOne addWatermark;
    @BindView(R.id.video_stitching)
    ItemFunctionLayoutOne videoStitching;
    @BindView(R.id.subtitles_tool)
    ItemFunctionLayoutTwo subtitlesTool;
    @BindView(R.id.clip_tool)
    ItemFunctionLayoutTwo clipTool;
    @BindView(R.id.watermark_tool)
    ItemFunctionLayoutTwo watermarkTool;
    @BindView(R.id.stitching_tool)
    ItemFunctionLayoutTwo stitchingTool;
    @BindView(R.id.top)
    HeadLayout top;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.indicator)
    LinearLayout indicator;

    private List<ImageView> indicatorImages = new ArrayList<>();
    private int mIndicatorSelectedResId = R.mipmap.ic_banner_xz;
    private int mIndicatorUnselectedResId = R.mipmap.ic_banner_wxz;
    private int lastPosition = 0;
    private List<BannerData> mList;
    private UserFragment userFragment;

    @Override
    protected int getLayouId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(50));
        layoutParams.topMargin = DisplayUtil.getStatusBarHeight(this);
        top.setLayoutParams(layoutParams);*/
        top.setOnLeftViewClickListener(v -> {
            setIntroFragment();
        });
        initBanner();
    }

    private void initBanner() {
        mList = new ArrayList<>();
        mList.add(new BannerData(R.mipmap.banner01,"", "CustomLayout", false));
        mList.add(new BannerData(R.mipmap.banner02,"", "Transformer", false));
        initIndicator();
        banner.setAutoPlay(true)
                .setPages(mList, new CustomViewHolder())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                //.setBannerAnimation(Transformer.Scale)
                .setBannerAnimation(ScaleTransformer.class)
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorImages.get((lastPosition + mList.size()) % mList.size()).setImageResource(mIndicatorUnselectedResId);
                indicatorImages.get((position + mList.size()) % mList.size()).setImageResource(mIndicatorSelectedResId);
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        indicatorImages.clear();
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams custom_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            custom_params.leftMargin = 2;
            custom_params.rightMargin = 2;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            indicator.addView(imageView, custom_params);
        }
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initAction() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void setIntroFragment() {
        userFragment = new UserFragment();
        Bundle bundle = new Bundle();
        userFragment.setArguments(bundle);
        if (!userFragment.isAdded()) {
            userFragment.show(getSupportFragmentManager(), "UserFragment");
        }
    }

    @OnClick({R.id.add_subtitles, R.id.video_clip, R.id.add_watermark, R.id.video_stitching, R.id.subtitles_tool, R.id.clip_tool, R.id.watermark_tool, R.id.stitching_tool})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_subtitles:
                VideoSelActivity.start(this,VideoSelActivity.ADD_SUBTITLE);
                break;
            case R.id.video_clip:
                VideoSelActivity.start(this,VideoSelActivity.VIDEO_CLIP);
                break;
            case R.id.add_watermark:
                VideoSelActivity.start(this,VideoSelActivity.ADD_WATERMARK);
                break;
            case R.id.video_stitching:
                VideoSelActivity.start(this,VideoSelActivity.VIDEO_STITCHING);
                break;
            case R.id.subtitles_tool:

                break;
            case R.id.clip_tool:

                break;
            case R.id.watermark_tool:

                break;
            case R.id.stitching_tool:

                break;
        }
    }
}
