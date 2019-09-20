package com.bavin.mohsen.backnardeban.Classes;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.bavin.mohsen.backnardeban.R;

public class BackgroundMusic extends AsyncTask<Void, Void, Void> {
    Activity activity;
    public BackgroundMusic( Activity activity){
        this.activity=activity;

    }
    @Override
    protected Void doInBackground(Void... voids) {
        MediaPlayer player = MediaPlayer.create(activity, R.raw.fantasy_game);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();

        return null;
    }
}
