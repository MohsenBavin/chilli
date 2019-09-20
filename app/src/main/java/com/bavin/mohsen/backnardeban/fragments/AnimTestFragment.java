package com.bavin.mohsen.backnardeban.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bavin.mohsen.backnardeban.Classes.TimeService;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.ShowFilmActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimTestFragment extends Fragment {

    private ImageView image1,image2,image3,mgtst,imageFilm,imageMessage,imageNotif;
    private ImageView buttonForward,buttonBack;
    private Toolbar toolbar;
    private static int state=1;
    private static boolean ok=true;
    private Menu menu;
    private MenuItem menuItem;
    private static int checkTimeFilm;
    Timer timerhome;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View v = inflater.inflate( R.layout.fragment_home, container, false );

//***********************************************************************************************************
//***************************** start of touch methods ******************************************************
        final GestureDetector gesture=new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return super.onDown( e );
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        //Toast.makeText( getContext(),"onFling has been called!",Toast.LENGTH_SHORT ).show();
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH  )
                                return false;
                            if (e1.getY()<700)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                //Toast.makeText( getContext(),"Right to Left",Toast.LENGTH_SHORT ).show();
                                /****************************/
                                if (ok==true) {
                                    setButtonForward();
                                    new Handler().postDelayed( new Runnable() {

                                        @Override
                                        public void run() {
                                            ok=true;

                                        }
                                    } , 400 ) ;
                                }
                                //****************************************************************************

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                //  Toast.makeText( getContext(),"Left to Right",Toast.LENGTH_SHORT ).show();

                                /*****************************/
                                if (ok==true) {

                                    setButtonBack();
                                    new Handler().postDelayed( new Runnable() {

                                        @Override
                                        public void run() {
                                            ok=true;

                                        }
                                    } , 400 ) ;
                                }
                                //******************************************************************************

                            }
                        } catch (Exception e) {
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                } );
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gesture.onTouchEvent(event);
                return true;
            }
        });

//******************************* end of touch methods**********************************************
//*************************************************************************************************
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

//******************************  Define View  ***********************************
        image1=view.findViewById( R.id.cir_image_1 );
        image2=view.findViewById( R.id.cir_image_2 );
        image3=view.findViewById( R.id.cir_image_3 );
        buttonForward=view.findViewById( R.id.btn_back );
        buttonBack=view.findViewById( R.id.btn_forward );
        mgtst=view.findViewById( R.id.imageView7 );
        imageMessage=view.findViewById( R.id.image_message );
        imageFilm=view.findViewById( R.id.image_Film );
        imageNotif=view.findViewById( R.id.image_notif );



//****************************** RunTimerService ************************************

//******************************Run timer & Set menu icon ****************************

        timerhome=new Timer(  );
        timerhome.schedule( new TimerTask() {
            @Override
            public void run() {

                if(TimeService.getTimerCountSrev() <= 0){
                    imageFilm.setImageResource( R.drawable.active_film );
                }
                else imageFilm.setImageResource( R.drawable.inactive_film );


            }
        },0,2000 );


//******************************  ClickListeners  ***********************************
/****** imageFilm ******************************************************************/


        imageFilm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TimeService.getTimerCountSrev() <= 0){
                    startActivity( new Intent( getContext(), ShowFilmActivity.class ) );

                }

            }
        } );
/****** Button Forward ***************************************************************/

        image2 .setVisibility(View.GONE);
        image3 .setVisibility(View.GONE);

        buttonForward.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ok==true) {
                    Animation animationButt= AnimationUtils.loadAnimation( getContext() , R.anim.butt_anim);
                    buttonForward.startAnimation( animationButt );
                    setButtonForward();
                    new Handler().postDelayed( new Runnable() {

                        @Override
                        public void run() {
                            ok=true;

                        }
                    } , 400 ) ;
                }
            }
        } );

        /****** Button Back**********************************************************************************/

        buttonBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (ok==true) {
                    Animation animationButt= AnimationUtils.loadAnimation( getContext() , R.anim.butt_anim);
                    buttonBack.startAnimation( animationButt );
                    setButtonBack();
                    new Handler().postDelayed( new Runnable() {

                        @Override
                        public void run() {
                            ok=true;

                        }
                    } , 400 ) ;
                }


            }
        } );
    }


    //*****************************   Animation methodes   **********************************
    public void setButtonForward(){



        if (state==1){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2_cp);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_cp);
            image2 .setVisibility(View.VISIBLE);
            image2.startAnimation( animation1 );
            image1.startAnimation( animation2 );
            image1 .setVisibility(View.GONE);
            state=2;

        }
        else if (state==2){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2_cp);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_cp);
            image3 .setVisibility(View.VISIBLE);
            image3.startAnimation( animation1 );
            image2.startAnimation( animation2 );
            image2 .setVisibility(View.GONE);
            state=3;

        }
        else if (state==3){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2_cp);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_cp);
            image1 .setVisibility(View.VISIBLE);
            image1.startAnimation( animation1 );
            image3.startAnimation( animation2 );
            image3 .setVisibility(View.GONE);
            state=1;

        }
        ok=false;
    }
    //****************************************************************
    public void setButtonBack(){

        if (state==3){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            image2 .setVisibility(View.VISIBLE);
            image2.startAnimation( animation1 );
            image3.startAnimation( animation2 );
            image3 .setVisibility(View.GONE);
            state=2;

        }
        else if (state==2){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            image1 .setVisibility(View.VISIBLE);
            image1.startAnimation( animation1 );
            image2.startAnimation( animation2 );
            image2 .setVisibility(View.GONE);
            state=1;
        }
        else if (state==1){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            image3 .setVisibility(View.VISIBLE);
            image3.startAnimation( animation1 );
            image1.startAnimation( animation2 );
            image1 .setVisibility(View.GONE);
            state=3;
        }
        ok=false;
    }

//******************************************************************************************


    @Override
    public void onResume() {
        super.onResume();
        state=1;
    }
}