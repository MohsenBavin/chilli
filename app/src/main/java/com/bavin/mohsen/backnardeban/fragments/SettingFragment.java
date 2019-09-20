package com.bavin.mohsen.backnardeban.fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.RatingbarDialog;
import com.bavin.mohsen.backnardeban.Classes.MainMusicService;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

ImageView image_sound_setting,image_music_setting;
ConstraintLayout con_music_setting,con_sound_setting,con_link_setting,con_questions_setting
        ,con_about_setting,con_rating_setting;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_setting, container, false );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        image_sound_setting=view.findViewById( R.id.image_sound_setting );
        image_music_setting=view.findViewById( R.id.image_music_setting );

        con_music_setting=view.findViewById( R.id.con_music_setting );
        con_sound_setting=view.findViewById( R.id.con_sound_setting );
        con_link_setting=view.findViewById( R.id.con_link_setting );
        con_questions_setting=view.findViewById( R.id.con_questions_setting );
        con_about_setting=view.findViewById( R.id.con_about_setting );
        con_rating_setting=view.findViewById( R.id.con_rating_setting );

        if(Hawk.get( "music" ).equals( "on" )){
            image_music_setting.setImageResource( R.drawable.sound );
        }else {
            image_music_setting.setImageResource( R.drawable.no_sound );

        }
        if(Hawk.get( "sound" ).equals( "on" )){
            image_sound_setting.setImageResource( R.drawable.speaker_up );
        }else {
            image_sound_setting.setImageResource( R.drawable.speaker_mute );

        }

        con_music_setting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String music= Hawk.get( "music" );
                if (music.equals( "on" )) {
                    Hawk.put("music","off" );
                    getActivity().stopService(new Intent( getActivity(), MainMusicService.class));
                    image_music_setting.setImageResource( R.drawable.no_sound );
                }
                else if (music.equals( "off" )){
                    Hawk.put( "music", "on" );
                    getActivity().startService( new Intent( getActivity(), MainMusicService.class ) );
                    image_music_setting.setImageResource( R.drawable.sound );
                }

            }
        } );

        con_sound_setting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sound= Hawk.get( "sound" );
                if (sound.equals( "on" )) {
                    Hawk.put("sound","off" );
                    image_sound_setting.setImageResource( R.drawable.speaker_mute );
                }
                else if (sound.equals( "off" )){

                    Hawk.put( "sound", "on" );
                    image_sound_setting.setImageResource( R.drawable.speaker_up );


                }
            }
        } );

        con_rating_setting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingbarDialog ratingbarDialog=new RatingbarDialog( getActivity() );
                ratingbarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ratingbarDialog.setCanceledOnTouchOutside(false);
                ratingbarDialog.show();
            }
        } );

    }

    }
