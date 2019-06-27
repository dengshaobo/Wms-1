package com.hzx.wms.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseArray;

import com.hzx.wms.R;


/**
 * Created by qinliang on 2018/5/11.
 */

public class SoundPlayUtils {
    // SoundPool对象
    private static SoundPool mSoundPlayer = new SoundPool(5,
            AudioManager.STREAM_SYSTEM, 0);
    private static SoundPlayUtils soundPlayUtils;
    private static SparseArray<Integer> soundSparse = new SparseArray<>();


    /**
     * 初始化
     */
    public static SoundPlayUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils();

            soundSparse.put(1, mSoundPlayer.load(context.getApplicationContext(), R.raw.fangxing, 1));
            soundSparse.put(2, mSoundPlayer.load(context.getApplicationContext(), R.raw.chayan, 1));
            soundSparse.put(3, mSoundPlayer.load(context.getApplicationContext(), R.raw.kouhuo, 1));
            soundSparse.put(4, mSoundPlayer.load(context.getApplicationContext(), R.raw.repeat, 1));
            soundSparse.put(5, mSoundPlayer.load(context.getApplicationContext(), R.raw.error, 1));
            soundSparse.put(6, mSoundPlayer.load(context.getApplicationContext(), R.raw.check, 1));
            soundSparse.put(7, mSoundPlayer.load(context.getApplicationContext(), R.raw.success, 1));
        }
        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        mSoundPlayer.play(soundSparse.get(soundID), 1, 1, 0, 0, 1);
    }

}
