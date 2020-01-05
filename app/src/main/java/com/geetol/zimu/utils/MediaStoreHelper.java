package com.geetol.zimu.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Looper;
import android.provider.MediaStore;

import com.geetol.zimu.bean.VideoBean;
import com.geetol.zimu.bean.VideoDir;
import com.gtdev5.geetolsdk.mylibrary.util.ToastUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.HEIGHT;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.Video.Thumbnails.DATA;
import static android.provider.MediaStore.Video.Thumbnails.WIDTH;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_ID;

/**
 * Created by ZL on 2019/5/16
 *
 * 媒体库帮助类
 */

public class MediaStoreHelper {
    public static class FetchMediaThread extends Thread {
        WeakReference<Context> contextWeakReference;
        VideoResultCallback resultCallback;

        public FetchMediaThread(Context context, VideoResultCallback callback) {
            this.contextWeakReference = new WeakReference<>(context);
            this.resultCallback = callback;
        }

        @Override
        public void run() {
            if (contextWeakReference.get() == null) return;
            try {
                ContentResolver contentResolver = contextWeakReference.get().getContentResolver();
                ArrayList<Cursor> cursorArrayList = new ArrayList<>();
                Cursor videoCursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Video.Media._ID,
                                MediaStore.Video.Media.DATA,
                                MediaStore.Video.Media.BUCKET_ID,
                                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                                MediaStore.Video.Media.DATE_ADDED,
                                MediaStore.Video.Media.SIZE,
                                MediaStore.Video.Media.WIDTH,
                                MediaStore.Video.Media.HEIGHT,
                                MediaStore.Video.VideoColumns.DURATION,
                                MediaStore.Video.Media.MIME_TYPE}
                        , MIME_TYPE + "=? or " + MIME_TYPE + "=? or " + MIME_TYPE + "=? or " + MIME_TYPE + "=? "
                        , new String[]{"video/mpeg", "video/mp4", "video/3gpp", "video/avi"}
                        , MediaStore.Images.Media.DATE_ADDED + " DESC");
                cursorArrayList.add(videoCursor);
                Cursor[] cursors = cursorArrayList.toArray(new Cursor[cursorArrayList.size()]);
                MergeCursor data = new MergeCursor(cursors);
                List<VideoDir> directories = new ArrayList<>();
                if (contextWeakReference.get() == null) return;
                VideoDir videoDirectoryAll = new VideoDir();
                videoDirectoryAll.setName("所有视频");
                videoDirectoryAll.setId(1);
                while (data.moveToNext()) {
                    int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
                    int bucketId = data.getInt(data.getColumnIndexOrThrow(BUCKET_ID));
                    String name = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
                    String path = data.getString(data.getColumnIndexOrThrow(DATA));
                    long size = data.getLong(data.getColumnIndexOrThrow(SIZE));
                    String mimeType = data.getString(data.getColumnIndexOrThrow(MIME_TYPE));
                    int width = data.getInt(data.getColumnIndexOrThrow(WIDTH));
                    int height = data.getInt(data.getColumnIndexOrThrow(HEIGHT));
                    long addDate = data.getInt(data.getColumnIndexOrThrow(DATE_ADDED));
                    if (size < 1) continue;
                    VideoBean video = new VideoBean(imageId, path, mimeType, width, height, size);
                    video.setAdddate(addDate);
                    long duration = data.getLong(data.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION));
                    video.setDuration(duration);
                    videoDirectoryAll.addVideo(video);
                    VideoDir videoDir = null;
                    for (VideoDir dir : directories) {
                        if (dir.getId() == bucketId) {
                            videoDir = dir;
                            break;
                        }
                    }
                    if (videoDir == null) {
                        videoDir = new VideoDir();
                        videoDir.setId(bucketId);
                        videoDir.setName(name);
                        videoDir.setCoverPath(path);
                        videoDir.setDateAdded(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));
                        directories.add(videoDir);
                    }
                    videoDir.addVideo(video);
                }
                data.close();
                Collections.sort(videoDirectoryAll.getVideos(), (lhs, rhs) -> {
                    if(lhs.getAdddate() == rhs.getAdddate()) return 0;
                    return lhs.getAdddate() >= rhs.getAdddate() ? -1 : 1;//按照添加时间进行降序排序
                });
                if (videoDirectoryAll.getVideos().size() > 0) {
                    videoDirectoryAll.setCoverPath(videoDirectoryAll.getVideos().get(0).getPath());
                }
                videoDirectoryAll.setCoverPath(videoDirectoryAll.getVideos().get(0).getPath());
                directories.add(0, videoDirectoryAll);
                if (resultCallback != null) {
                    resultCallback.onResultCallback(directories);
                }
            } catch (Exception e) {
                Looper.prepare();//增加部分
                e.printStackTrace();
                ToastUtils.showShortToast("获取视频列表失败，请检查是否有视频");
                Looper.loop();//增加部分
            }
        }
    }

    public static void getVideoDirs(Context context, VideoResultCallback callback) {
        new FetchMediaThread(context, callback).start();
    }

    public interface VideoResultCallback {
        void onResultCallback(List<VideoDir> dirs);
    }
}
