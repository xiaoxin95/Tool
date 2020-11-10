package com.unipock.unipaytool.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 音频类
 */
public class MediaUtils {

    private static MediaPlayer player = new MediaPlayer();

    public static void playAudio(Context mContext,int resRawId) {
        if (player.isPlaying()) {
            return;
        }
        player.reset();
        try {
            AssetFileDescriptor file = mContext.getResources().openRawResourceFd(resRawId);
            try {
                player.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                if (!player.isPlaying()){
                    player.prepare();
                    player.start();
                }
            } catch (IOException e) {
                player = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
