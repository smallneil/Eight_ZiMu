package com.geetol.videoedit.blocks.mediaCodec.bigflake;


import com.geetol.videoedit.base.activity.RVBaseActivity;
import com.geetol.videoedit.base.beans.ClassBean;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.cameraToMpeg.CameraToMpegActivity;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.decodeEditEncode.DecodeEditEncodeActivity;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.encodeAndMux.EncodeAndMuxActivity;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.encodeDecode.EncodeDecodeActivity;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.extractDecodeEditEncodeMux.ExtractDecodeEditEncodeMuxActivity;
import com.geetol.videoedit.blocks.mediaCodec.bigflake.extractMpegFrames.ExtractMpegFramesActivity;

import java.util.List;

public class BigflakeActivity extends RVBaseActivity {

    @Override
    public List<ClassBean> initData() {
        mClassBeans.add(new ClassBean("EncodeAndMux",EncodeAndMuxActivity.class));
        mClassBeans.add(new ClassBean("CameraToMpeg",CameraToMpegActivity.class));
        mClassBeans.add(new ClassBean("EncodeDecode",EncodeDecodeActivity.class));
        mClassBeans.add(new ClassBean("ExtractDecodeEditEncodeMux",ExtractDecodeEditEncodeMuxActivity.class));
        mClassBeans.add(new ClassBean("DecodeEditEncodeActivity",DecodeEditEncodeActivity.class));
        mClassBeans.add(new ClassBean("ExtractMpegFramesActivity",ExtractMpegFramesActivity.class));
        return mClassBeans;
    }
}
