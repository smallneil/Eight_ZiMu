package com.geetol.videoedit.blocks;


import com.geetol.videoedit.base.activity.RVBaseActivity;
import com.geetol.videoedit.base.beans.ClassBean;
import com.geetol.videoedit.blocks.audioRecord.AudioRecordActivity;
import com.geetol.videoedit.blocks.mediaCodec.MediaCodecActivity;
import com.geetol.videoedit.blocks.mediaExtractor.MediaExtractorActivity;
import com.geetol.videoedit.blocks.mediaMuxer.MediaMuxerActivity;
import com.geetol.videoedit.blocks.mediaMuxer.functions.CreateVideoAddAudioToMp4;
import com.geetol.videoedit.blocks.others.changeHue.ChangeHueActivity;

import java.util.List;

public class BlocksActivity extends RVBaseActivity {

    @Override
    public List<ClassBean> initData() {
        mClassBeans.add(new ClassBean("修改hue", ChangeHueActivity.class));
        mClassBeans.add(new ClassBean("AudioRecord", AudioRecordActivity.class));
        mClassBeans.add(new ClassBean("MediaCodec", MediaCodecActivity.class));
        mClassBeans.add(new ClassBean("MediaExtractor", MediaExtractorActivity.class));
        mClassBeans.add(new ClassBean("MediaMuxer", MediaMuxerActivity.class));
        mClassBeans.add(new ClassBean("合成", CreateVideoAddAudioToMp4.class));
        return mClassBeans;
    }
}
