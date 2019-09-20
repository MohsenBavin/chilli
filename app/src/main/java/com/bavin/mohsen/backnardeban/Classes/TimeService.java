package com.bavin.mohsen.backnardeban.Classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public  class  TimeService extends Service {
    Timer timerServ;
   private static long timerCountSrev;
   Context context;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timerCountSrev=1200000;

          timerServ=new Timer(  );
          timerServ.schedule( new TimerTask() {
              @Override
              public void run() {

                  if (timerCountSrev > 0) {
                      timerCountSrev -= 5000;

                  }
                  else if(timerCountSrev==0) {

                      timerServ.cancel();
                  }

              }
          },0,5000 );

        return START_STICKY;
    }

    //@androidx.annotation.Nullable
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static long getTimerCountSrev(){
        return timerCountSrev;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

    }
}
