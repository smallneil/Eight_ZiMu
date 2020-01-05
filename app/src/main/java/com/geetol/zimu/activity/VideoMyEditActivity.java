package com.geetol.zimu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geetol.videoedit.base.utils.DisplayUtil;
import com.geetol.videoedit.base.utils.StatusBarUtil;
import com.geetol.videoedit.whole.createVideoByVoice.localEdit.MediaPlayerWrapper;
import com.geetol.videoedit.whole.createVideoByVoice.localEdit.VideoInfo;
import com.geetol.videoedit.whole.createVideoByVoice.localEdit.VideoPreviewView;
import com.geetol.videoedit.whole.editVideo.view.BaseImageView;
import com.geetol.videoedit.whole.editVideo.view.BubbleTextView;
import com.geetol.videoedit.whole.editVideo.view.StickInfoImageView;
import com.geetol.videoedit.whole.editVideo.view.StickerView;
import com.geetol.videoedit.whole.editVideo.view.VideoEditView;
import com.geetol.videoedit.whole.record.other.MagicFilterType;
import com.geetol.videoedit.whole.record.ui.SlideGpuFilterGroup;
import com.geetol.zimu.adapter.TabFragmentAdapter;
import com.geetol.zimu.base.BaseActivity;
import com.geetol.zimu.fragment.ListFragment;
import com.geetol.zimu.widget.HeadLayout;
import com.shichai.zimu.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoMyEditActivity extends BaseActivity implements SlideGpuFilterGroup.OnFilterChangeListener, MediaPlayerWrapper.IMediaCallback, VideoEditView.OnSelectTimeChangeListener {
    //@BindView(R.id.bottom)
    //LinearLayout bottom;
    private String TAG = VideoMyEditActivity.class.getSimpleName();
    @BindView(R.id.top)
    HeadLayout top;
    @BindView(R.id.video_preview)
    VideoPreviewView mVideoView;
    @BindView(R.id.rl_content_root)
    FrameLayout mContentRootView;
    @BindView(R.id.ll_edit_seekbar)
    VideoEditView videoEditView;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pop_video_percent_tv)
    TextView mPopVideoPercentTv;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.pop_video_loading_fl)
    FrameLayout mPopVideoLoadingFl;
    private ArrayList<String> mSelectVideos;
    private int mType;

    private String[] tietu = {"涂鸦", "贴图"};
    private String[] zimu = {"颜色", "样式"};
    private List<Fragment> frgList = new ArrayList<>();
    private TabFragmentAdapter mAdapter;
    private float mPixel = 1.778f;
    public String mVideoRotation = "90";
    public int mVideoHeight, mVideoWidth, mVideoDuration; //mIsNotComeLocal 1表示拍摄,mIsAnswer
    private boolean isPlayVideo;
    private boolean hasSelectStickerView;
    private long currentTime;
    //当前处于编辑状态的贴纸
    private StickerView mCurrentView;
    //当前处于编辑状态的气泡
    private BubbleTextView mCurrentEditTextView;
    //存储贴纸列表
    private ArrayList<BaseImageView> mViews = new ArrayList<>();
    private ArrayList<StickInfoImageView> stickerViews = new ArrayList<>();
    private List<Bitmap> mThumbBitmap = new ArrayList<>();
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mThumbBitmap != null) {
                mThumbBitmap.add(msg.arg1, (Bitmap) msg.obj);
            }
        }
    };

    public static void start(Activity activity, int mType, ArrayList<String> mSelectVideos) {
        Intent intent = new Intent(activity, VideoMyEditActivity.class);
        intent.putExtra("mType", mType);
        intent.putStringArrayListExtra("videoList", mSelectVideos);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_video_myedit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("mType", 1);
        mSelectVideos = getIntent().getStringArrayListExtra("videoList");
        initIndicate();
    }

    @Override
    protected void initDatas() {
        initThumbs();
        mVideoView.setVideoPath(mSelectVideos);
        initSetParam();
        initListener();
        mThumbBitmap.clear();
    }

    private void initListener() {
        mVideoView.setOnFilterChangeListener(this);
        mVideoView.setIMediaCallback(this);
        videoEditView.setOnSelectTimeChangeListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mPopVideoLoadingFl != null && mPopVideoLoadingFl.getVisibility() == View.VISIBLE) {
            Log.e(TAG, "dispatchTouchEvent: ");
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    protected void initAction() {

    }


    /**
     * 初始化缩略图
     */
    private void initThumbs() {
        final MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        mediaMetadata.setDataSource(mContext, Uri.parse(mSelectVideos.get(0)));
        try {
            mVideoRotation = mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            mVideoWidth = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            mVideoHeight = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            finish();
        }
        mPixel = (float) mVideoHeight / (float) mVideoWidth;
        mVideoDuration = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        Log.e(TAG, "mVideoDuration:" + mVideoDuration);
        videoEditView.setTotalTime(mVideoDuration + 100);
        final int frame = mVideoDuration / (2 * 1000);
        Log.e(TAG, "frame:" + frame);
        final int frameTime;
        if (frame > 0) {
            frameTime = mVideoDuration / frame * 1000;
        } else {
            frameTime = 1 * 1000;
        }
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                for (int x = 0; x < frame; x++) {
                    Bitmap bitmap = mediaMetadata.getFrameAtTime(frameTime * x, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    Message msg = myHandler.obtainMessage();
                    msg.obj = bitmap;
                    msg.arg1 = x;
                    myHandler.sendMessage(msg);
                }
                mediaMetadata.release();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (mThumbBitmap != null) {
                    videoEditView.addImageView(mThumbBitmap);
                }
            }
        }.execute();
    }

    private void initSetParam() {
        ViewGroup.LayoutParams layoutParams = mContentRootView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams1 = mVideoView.getLayoutParams();
        if (!TextUtils.isEmpty(mVideoRotation) && mVideoRotation.equals("0") && mVideoWidth > mVideoHeight || mVideoRotation.equals("180") && mVideoWidth > mVideoHeight) {//本地视频横屏
            layoutParams.width = 1120;
            layoutParams.height = 630;
            layoutParams1.width = 1120;
            layoutParams1.height = 630;
        } else {
//            layoutParams.width = (int) (mVideoWidth * StaticFinalValues.VIDEO_WIDTH_HEIGHT);
            layoutParams.width = 630;
            layoutParams.height = 1120;
            layoutParams1.width = 630;
            layoutParams1.height = 1120;
        }
        mContentRootView.setLayoutParams(layoutParams);
        mVideoView.setLayoutParams(layoutParams1);
    }

    @Override
    public void onFilterChange(MagicFilterType type) {
        this.filterType = type;
    }

    @Override
    public void onVideoPrepare() {
        mHandler.sendEmptyMessage(VIDEO_PREPARE);
    }

    @Override
    public void onVideoStart() {
        mHandler.sendEmptyMessage(VIDEO_START);
    }

    @Override
    public void onVideoPause() {
        mHandler.sendEmptyMessage(VIDEO_PAUSE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /*mVideoView.seekTo(0);
        mVideoView.start();*/
    }

    @Override
    public void onVideoChanged(VideoInfo info) {

    }

    @Override
    public void selectTimeChange(long startTime, long endTime) {
        if (mViews == null || mViews.size() == 0) {
            return;
        }
        int position;
        if (hasSelectStickerView) {
            position = mViews.indexOf(mCurrentView);
        } else {
            position = mViews.indexOf(mCurrentEditTextView);
        }
        if (position != -1) {
            mViews.get(position).setStartTime(startTime);
            mViews.get(position).setEndTime(endTime);
        }
    }

    @Override
    public void playChange(boolean isPlayVideo) {
        Log.e(TAG, "播放状态变化");
        this.isPlayVideo = isPlayVideo;
        if (isPlayVideo) {
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
            if (mCurrentEditTextView != null) {
                mCurrentEditTextView.setInEdit(false);
            }
        } else {
            for (StickInfoImageView stickInfoImageView : stickerViews) {  //清空动态贴纸
                mContentRootView.removeView(stickInfoImageView);
            }
            stickerViews.clear();
        }
        try {
            if (isPlayVideo) {
//                    mVideoView.seekTo(0);
                mVideoView.start();
            } else {
                mVideoView.pause();
            }
        } catch (Exception e) {
            Log.e(TAG, "异常:" + e);
        }
    }

    @Override
    public void videoProgressUpdate(long currentTime, boolean isVideoPlaying) {
        this.currentTime = currentTime;
        if (!isVideoPlaying) {
            try {
                Log.e(TAG, "currentTime:" + currentTime);
                mVideoView.seekTo((int) currentTime);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "异常:" + e);
            }
        }else {
            Log.e(TAG, "播放中currentTime:" + currentTime);
        }
        for (int i = 0; i < mViews.size(); i++) {              ////遍历显示静态图
            BaseImageView baseImageView = mViews.get(i);
            long startTime = baseImageView.getStartTime();
            long endTime = baseImageView.getEndTime();
            if (currentTime >= startTime && currentTime <= endTime) {
                if (baseImageView.isGif()) {
                    if (currentTime != 0) {
                        int frameIndex = baseImageView.getFrameIndex();
                        ((StickerView) baseImageView).changeBitmap(baseImageView.getBitmaps().get(frameIndex));
                        mViews.get(i).setFrameIndex(frameIndex + 1);
                    }
                    baseImageView.setVisibility(View.VISIBLE);
                } else {
                    baseImageView.setVisibility(View.VISIBLE);
                }
            } else {
                baseImageView.setVisibility(View.GONE);
            }
        }
    }

    //视频播放接口
    private boolean isDestroy;
    private boolean isPlaying = false;
    static final int VIDEO_PREPARE = 0;
    static final int VIDEO_START = 1;
    static final int VIDEO_UPDATE = 2;
    static final int VIDEO_PAUSE = 3;
    static final int VIDEO_CUT_FINISH = 4;
    static final int CLIP_VIDEO_PERCENT = 5;
    static final int AUTO_PAUSE = 6;
    private MagicFilterType filterType = MagicFilterType.NONE;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLIP_VIDEO_PERCENT:
                    float aFloat = (float) msg.obj;
                    mPopVideoPercentTv.setText(String.valueOf((int) (aFloat * 100)) + "%");
                    break;
                case VIDEO_PREPARE:
                    Executors.newSingleThreadExecutor().execute(update);
                    mHandler.sendEmptyMessageDelayed(AUTO_PAUSE,50);
                    break;
                case VIDEO_START:
                    isPlaying = true;
                    break;
                case VIDEO_UPDATE:
                  /*  int curDuration = mVideoView.getCurDuration();
                    if (curDuration > startPoint + clipDur) {
                        mVideoView.seekTo(startPoint);
                        mVideoView.start();
                    }*/
                    break;
                case VIDEO_PAUSE:
                    isPlaying = false;
                    break;
                case VIDEO_CUT_FINISH:
                    mPopVideoPercentTv.setText("0%");
                    mPopVideoLoadingFl.setVisibility(View.GONE);
                    //TODO　已经渲染完毕了　
                    break;
                case AUTO_PAUSE:
                    mVideoView.pause();
                    break;
            }
        }
    };

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            while (!isDestroy) {
                if (!isPlaying) {
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                mHandler.sendEmptyMessage(VIDEO_UPDATE);
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void initIndicate() {
        frgList.add(ListFragment.newInstance(1));
        frgList.add(ListFragment.newInstance(2));
        mAdapter=new TabFragmentAdapter(frgList,getSupportFragmentManager(),this);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tietu == null ? 0 : tietu.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(tietu[index]);
                colorTransitionPagerTitleView.setTextSize(15);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(R.color.main_color));
                indicator.setLineHeight(DisplayUtil.dipToPx(2));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void videoPlay() {
        Log.e(TAG,"currentTime:"+currentTime+",mVideoDuration:"+mVideoDuration);
        if(currentTime >= mVideoDuration){
            return;
        }
        for (StickInfoImageView stickInfoImageView : stickerViews) {  //清空gif图
            mContentRootView.removeView(stickInfoImageView);
        }
        stickerViews.clear();
        for (BaseImageView baseImageView : mViews) {
            baseImageView.setVisibility(View.GONE);
//            addGifView(baseImageView);
        }
        videoEditView.videoPlay(mViews);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_content_root)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_content_root:
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }

        if (isPlayVideo) {
            videoPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        isDestroy = true;
        mVideoView.onDestroy();
        if (mThumbBitmap != null) {
            for (int i = 0; i < mThumbBitmap.size(); i++) {
                mThumbBitmap.get(i).recycle();
            }
            mThumbBitmap = null;
        }
        System.gc();
    }
}
