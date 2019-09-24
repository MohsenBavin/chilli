package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.bavin.mohsen.backnardeban.R;

import java.util.Timer;

public class ProgressDialog extends Dialog  {
    public ProgressDialog( Activity activity) {
        super( activity );
    }

    private  int pStatus=100,pStatus2=0 ;
    private Handler handler = new Handler();
    private ProgressBar progressStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_prgress );
        progressStart =  findViewById(R.id.progressStart );
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus2 <= 39) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressStart.setProgress(pStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus2++;
                }
            }
        }).start();
    }
}
