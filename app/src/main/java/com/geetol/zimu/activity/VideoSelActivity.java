package com.geetol.zimu.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geetol.zimu.adapter.VideoAllAdapter;
import com.geetol.zimu.base.BaseActivity;
import com.geetol.zimu.bean.VideoBean;
import com.geetol.zimu.bean.VideoDir;
import com.geetol.zimu.utils.MediaStoreHelper;
import com.geetol.zimu.widget.HeadLayout;
import com.gtdev5.geetolsdk.mylibrary.util.ToastUtils;
import com.huantansheng.easyphotos.utils.permission.PermissionUtil;
import com.shichai.zimu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoSelActivity extends BaseActivity {
    public static final int ADD_SUBTITLE = 1;
    public static final int VIDEO_CLIP = 2;
    public static final int ADD_WATERMARK = 3;
    public static final int VIDEO_STITCHING = 4;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_permission)
    TextView tvPermission;
    @BindView(R.id.no_pic)
    ImageView noPic;
    @BindView(R.id.rl_permissions_view)
    RelativeLayout rlPermissionsView;
    @BindView(R.id.top)
    HeadLayout top;
    private List<VideoDir> mVideoDirs = new ArrayList<>();
    private List<VideoBean> mVideos = new ArrayList<>();
    private List<VideoBean> mSelectVideos = new ArrayList<>();
    private VideoAllAdapter videoAllAdapter;
    private int pos = 0, mType = 1, videoCount = 1;

    public static void start(Activity activity, int mType) {
        Intent intent = new Intent(activity, VideoSelActivity.class);
        intent.putExtra("mType", mType);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_video_sel;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra("mType", 1);
        top.setOnRightViewClickListener(v -> {
            if (mSelectVideos!=null&&mSelectVideos.size()>0) {
                ArrayList<String> vidoeList= new ArrayList<>();
                for (int i=0;i<mSelectVideos.size();i++){
                    vidoeList.add(mSelectVideos.get(i).getPath());
                }
                VideoMyEditActivity.start(this,VideoSelActivity.ADD_SUBTITLE,vidoeList);
            }else{
                ToastUtils.showShortToast("您还未选择视频!");
            }
        });
        if (mType == VIDEO_STITCHING) {
            videoCount = 2;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        videoAllAdapter = new VideoAllAdapter(this);
        recyclerView.setAdapter(videoAllAdapter);
        videoAllAdapter.setOnItemClickListener(position -> {
            if (videoCount == 1) {//
                VideoBean videobean = videoAllAdapter.getAllData().get(pos);
                if (videobean.isSel() && pos != position) {
                    videobean.setSel(false);
                    if (recyclerView.getLayoutManager().findViewByPosition(pos) != null) {
                        CheckBox checkbox = recyclerView.getLayoutManager().findViewByPosition(pos).findViewById(R.id.checkbox);
                        if (checkbox != null) {
                            checkbox.setChecked(videobean.isSel());
                        }
                    }
                }
                mSelectVideos.clear();
                this.pos = position;
            }
            VideoBean videobean = videoAllAdapter.getAllData().get(position);
            if (!videobean.isSel() && mSelectVideos.size() >= videoCount) {
                ToastUtils.showShortToast("最多选择两个视频");
            } else {
                videobean.setSel(!videobean.isSel());
                if (recyclerView.getLayoutManager().findViewByPosition(position) != null) {
                    CheckBox checkbox = recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.checkbox);
                    if (checkbox != null) {
                        checkbox.setChecked(videobean.isSel());
                    }
                }
                if (videobean.isSel()) {
                    mSelectVideos.add(videobean);
                } else {
                    mSelectVideos.remove(videobean);
                }
            }
        });

        videoAllAdapter.setOnItemLongClickListener((position) -> {
            /*String path = mVideos.get(position).getPath();
            VideoPlayerActivity2.launch(VideoSelActivity.this,path);
            ToastUtils.showLongToast(path);*/
            return false;
        });
    }

    @Override
    protected void initDatas() {
        if (PermissionUtil.checkAndRequestPermissionsInActivity(this, getPermissions())) {
            getData();
        }
    }

    /**
     * 获取读写权限
     */
    public String[] getPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                .permission.READ_EXTERNAL_STORAGE};
    }

    /**
     * 获取数据
     */
    private void getData() {
        createFile(false);
        MediaStoreHelper.getVideoDirs(this, dirs -> {
            if (dirs != null && dirs.size() > 0) {
                mVideoDirs = dirs;
                //mVideos = mVideoDirs.get(0).getVideos();
                videoAllAdapter.addAll(dirs.get(0).getVideos());
                runOnUiThread(() -> {
                    videoAllAdapter.notifyDataSetChanged();
                    rlPermissionsView.setVisibility(View.GONE);
                });
            } else {
                runOnUiThread(() -> {
                    rlPermissionsView.setVisibility(View.VISIBLE);
                    tvPermission.setText("没有找到视频喔！");
                });
            }
        });
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
}
