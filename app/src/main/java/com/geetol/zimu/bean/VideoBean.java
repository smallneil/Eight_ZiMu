package com.geetol.zimu.bean;

import java.io.Serializable;


public class VideoBean implements Serializable {
    private int id;
    private String path;
    private String mimetype;
    private long duration;
    private int width, height;
    private long size;
    private long adddate;
    private boolean isSel = false;//是否选中

    public VideoBean() {}

    public VideoBean(int id, String path, String mimetype, int width, int height, long size) {
        this.id = id;
        this.path = path;
        this.mimetype = mimetype;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoBean)) return false;
        VideoBean video = (VideoBean) o;
        return id == video.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getAdddate() {
        return adddate;
    }

    public void setAdddate(long adddate) {
        this.adddate = adddate;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", path='" + path + '\'' + ", mimetype='" + mimetype +
                '\'' + ", duration=" + duration + ", width=" + width + ", height=" + height + ", size=" + size + ", adddate=" + adddate + ", isSel=" + isSel + '}';
    }
}
