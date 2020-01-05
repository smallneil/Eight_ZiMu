package com.geetol.videoedit.whole.pickvideo.callback;

public interface OnSelectStateListener<T> {
        void OnSelectStateChanged(boolean state, T file);
    }