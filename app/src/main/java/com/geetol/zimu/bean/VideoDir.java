package com.geetol.zimu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class VideoDir implements Serializable {
    private int id;
    private String coverPath; // 封面图片
    private String name; // 名称
    private long dateAdded;
    private boolean selected = false;
    private List<VideoBean> videos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoDir)) return false;
        VideoDir directory = (VideoDir) o;
        return id == directory.getId();
    }

    public void setVideos(List<VideoBean> videos) {
        if (videos == null) return;
        for (int i = 0, j = 0, num = videos.size(); i < num; i++) {
            VideoBean video = videos.get(i);
            if (video == null) {
                videos.remove(j);
            } else {
                j++;
            }
        }
        this.videos = videos;
    }

    public List<String> getVideoPath() {
        List<String> paths = new ArrayList<>(videos.size());
        for (VideoBean video : videos) {
            paths.add(video.getPath());
        }
        return paths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<VideoBean> getVideos() {
        return videos;
    }

    public void addVideo(VideoBean video) {
        videos.add(video);
    }
}
