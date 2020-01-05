package com.geetol.videoedit.blocks.audioRecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geetol.videoedit.R;
import com.geetol.videoedit.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.audio_record_btn)
    public void onViewClicked() {
        new AudioRecordDemo().getNoiseLevel();
    }

}
