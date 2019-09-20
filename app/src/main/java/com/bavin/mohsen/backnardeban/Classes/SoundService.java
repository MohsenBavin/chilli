package com.bavin.mohsen.backnardeban.Classes;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bavin.mohsen.backnardeban.R;

public class SoundService extends Service {
    MediaPlayer player;

    //@androidx.annotation.Nullable
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.fantasy_game); //select music file
        player.setLooping(true); //set looping
        player.setVolume(0.2f, 0.2f);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }

}
