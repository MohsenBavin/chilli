package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bavin.mohsen.backnardeban.ChanceChalengeMachActivity;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

public class GameSettingDialog extends Dialog implements View.OnClickListener {
    public Activity activity;

public ConstraintLayout con_resume_game,con_music_game,con_sound_game,con_exite_game;
public ImageView img_resume_game,img_music_game,img_sound_game,img_exite_game;


    public GameSettingDialog(Activity activity) {
        super( activity );
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.dialog_game_setting );
        con_resume_game=findViewById( R.id.con_resume_game );
        con_music_game=findViewById( R.id.con_music_game );
        con_sound_game=findViewById( R.id.con_sound_game );
        con_exite_game=findViewById( R.id.con_exite_game );

        con_resume_game.setOnClickListener( this );
        con_music_game.setOnClickListener( this );
        con_sound_game.setOnClickListener( this );
        con_exite_game.setOnClickListener( this );

        img_music_game=findViewById( R.id.img_music_game );
        img_sound_game=findViewById( R.id.img_sound_game );

        if(Hawk.get( "music" ).equals( "on" )){
            img_music_game.setImageResource( R.drawable.sound );
        }else {
            img_music_game.setImageResource( R.drawable.no_sound );

        }
        if(Hawk.get( "sound" ).equals( "on" )){
            img_sound_game.setImageResource( R.drawable.speaker_up );
        }else {
            img_sound_game.setImageResource( R.drawable.speaker_mute );

        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.con_resume_game:

                dismiss();
                break;
            case R.id.con_music_game:
                String music= Hawk.get( "music" );
                if (music.equals( "on" )) {
                    Hawk.put("music","off" );
                    activity.stopService(new Intent( activity, SoundService.class));
                    img_music_game.setImageResource( R.drawable.no_sound );
                }
                else if (music.equals( "off" )){

                    Hawk.put( "music", "on" );
                    activity.startService( new Intent( activity, SoundService.class ) );
                    img_music_game.setImageResource( R.drawable.sound );


                }

                break;
            case R.id.con_sound_game:

                String sound= Hawk.get( "sound" );
                if (sound.equals( "on" )) {
                    Hawk.put("sound","off" );
                    img_sound_game.setImageResource( R.drawable.speaker_mute );
                }
                else if (sound.equals( "off" )){

                    Hawk.put( "sound", "on" );
                    img_sound_game.setImageResource( R.drawable.speaker_up );


                }
                break;
            case R.id.con_exite_game:
                WarningExiteMachDialog dialog=new WarningExiteMachDialog( activity );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dismiss();

                break;
            default:
                break;
        }

    }
}