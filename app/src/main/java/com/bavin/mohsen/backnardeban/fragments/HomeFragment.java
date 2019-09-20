package com.bavin.mohsen.backnardeban.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.ChanceChalengeMachActivity;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.ShowTimerDialog;
import com.bavin.mohsen.backnardeban.SelectChanceChallengeActivity;
import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.SelectLessonStudyActivity;
import com.bavin.mohsen.backnardeban.ShowFilmActivity;
import com.bavin.mohsen.backnardeban.SocketTestActivity;
import com.bavin.mohsen.backnardeban.StudyChallengeActivity;
import com.bavin.mohsen.backnardeban.UserActivity;
import com.bavin.mohsen.backnardeban.VSchance;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment
{
    private ImageView img_chance_challenge,img_friendly_challenge,img_study_challenge,mgtst,imageFilm,imageMessage,imageNotif;
    private ImageView buttonForward,buttonBack;
    private CircleImageView circleImageUser;
    private TextView text_userName,text_level,text_diamond;
    private Toolbar toolbar;
    private static int state=1;
    private static boolean ok=true,image1Click=false;
    private Menu menu;
    private MenuItem menuItem;
    private static int checkTimeFilm;
    Timer timerhome;
    private static long timewaite,nowtime;
    private String nameUser="";
    private static boolean enableFilm;
    private ViewGroup mainLayout;
    private int xDelta;
    private int yDelta;
    float  x1=0 , x2=0, y1, y2, dx, dy;



    public static HomeFragment newHome(String nameUser){
        HomeFragment homeFragment=new HomeFragment();
        Bundle args=new Bundle();
        args.putString("name",nameUser);

        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.nameUser=getArguments().getString("name");
        Hawk.init(getContext()).build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate( R.layout.fragment_home, container, false );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

//******************************  Define View  ***********************************
        img_chance_challenge=view.findViewById( R.id.cir_image_1 );
        img_friendly_challenge=view.findViewById( R.id.cir_image_2 );
        img_study_challenge=view.findViewById( R.id.cir_image_3 );
        buttonForward=view.findViewById( R.id.btn_back );
        buttonBack=view.findViewById( R.id.btn_forward );
        mgtst=view.findViewById( R.id.imageView7 );
        imageMessage=view.findViewById( R.id.image_message );
        imageFilm=view.findViewById( R.id.image_Film );
        imageNotif=view.findViewById( R.id.image_notif );
        mainLayout=view.findViewById( R.id.constraint_homeFrag );
        text_userName=view.findViewById( R.id.text_user_name );
        text_level=view.findViewById( R.id.text_level );
        circleImageUser=view.findViewById( R.id.circleImage_user );
        text_diamond=view.findViewById( R.id.text_diamond );


//*********************************** Toolbar *************************************
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getDelegate().setSupportActionBar(toolbar);


        circleImageUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim = new ScaleAnimation(
                        .97f, 1f,
                        .97f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);
                anim.setFillAfter(true);
                v.startAnimation(anim);
                startActivity( new Intent( getContext(), UserActivity.class ) );
                getActivity().finish();

            }
        } );

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
                        final int SWIPE_MAX_OFF_PATH = 650;
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

                                else if (e1.getX() - e2.getX()<SWIPE_MIN_DISTANCE
                                        &&image1Click==true) {
                                    Toast.makeText( getContext(),"img_chance_challenge click",Toast.LENGTH_SHORT ).show();
                                }
                                //******************************************************************************

                            }
                        } catch (Exception e) {
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                } );



        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gesture.onTouchEvent(event);
                return true;
            }
        });
//******************************* end of touch methods**********************************************
//*************************************************************************************************

//****************************** RunTimerService ************************************

//******************************  ClickListeners  ***********************************
/****** imageFilm ******************************************************************/

mgtst.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
} );
imageNotif.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent( getContext(), SocketTestActivity.class  ) );
        getActivity().finish();



    }
} );

imageMessage.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent( getContext(), SelectLessonStudyActivity.class  ) );
        getActivity().finish();


    }
} );
/***** imageFilm **********************************************************************/
        imageFilm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText( getContext(),""+time,Toast.LENGTH_SHORT ).show();
                if (enableFilm==true) {
                    startActivity( new Intent( getContext(), ShowFilmActivity.class ) );
                    getActivity().finish();
                }
               else {
                     ShowTimerDialog showTimerDialog = new ShowTimerDialog(getActivity());
                    showTimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    showTimerDialog.show();

                }


            }
        } );
/****** Button Forward ***************************************************************/

        img_friendly_challenge .setVisibility(View.GONE);
        img_study_challenge .setVisibility(View.GONE);

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

/***** img_chance_challenge click**********************************************************************************/
//****************************************************************************************************
        img_chance_challenge.setOnTouchListener( onTouchListener() );
        img_friendly_challenge.setOnTouchListener( onTouchListener() );
        img_study_challenge.setOnTouchListener( onTouchListener() );


    }

    //*****************************   Animation methodes   **********************************
    //*************//
    public void setButtonForward(){



        if (state==1){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward);
            img_friendly_challenge .setVisibility(View.VISIBLE);
            img_friendly_challenge.startAnimation( animation1 );
            img_chance_challenge.startAnimation( animation2 );
            img_chance_challenge .setVisibility(View.GONE);
            state=2;

        }
        else if (state==2){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward);
            img_study_challenge .setVisibility(View.VISIBLE);
            img_study_challenge.startAnimation( animation1 );
            img_friendly_challenge.startAnimation( animation2 );
            img_friendly_challenge .setVisibility(View.GONE);
            state=3;

        }
        else if (state==3){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_forward);
            img_chance_challenge .setVisibility(View.VISIBLE);
            img_chance_challenge.startAnimation( animation1 );
            img_study_challenge.startAnimation( animation2 );
            img_study_challenge .setVisibility(View.GONE);
            state=1;

        }
        ok=false;
    }
//********//
    public void setButtonBack(){

        if (state==3){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            img_friendly_challenge .setVisibility(View.VISIBLE);
            img_friendly_challenge.startAnimation( animation1 );
            img_study_challenge.startAnimation( animation2 );
            img_study_challenge .setVisibility(View.GONE);
            state=2;

        }
        else if (state==2){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            img_chance_challenge .setVisibility(View.VISIBLE);
            img_chance_challenge.startAnimation( animation1 );
            img_friendly_challenge.startAnimation( animation2 );
            img_friendly_challenge .setVisibility(View.GONE);
            state=1;
        }
        else if (state==1){
            Animation animation2= AnimationUtils.loadAnimation( getContext() , R.anim.move_back_2);
            Animation animation1= AnimationUtils.loadAnimation( getContext() , R.anim.move_back);
            img_study_challenge .setVisibility(View.VISIBLE);
            img_study_challenge.startAnimation( animation1 );
            img_chance_challenge.startAnimation( animation2 );
            img_chance_challenge .setVisibility(View.GONE);
            state=3;
        }
        ok=false;
    }

//***************//


//******************************************************************************************


    @Override
    public void onResume() {
        super.onResume();
        state=1;
        timewaite = Hawk.get( "time" );
        timeRun();

        Hawk.init(getContext()).build();
        String name=Hawk.get("username");
        text_userName.setText( name);
        String level=Hawk.get("level");
        String field=Hawk.get("field");
        String textLevel=""+level+" "+field;
        text_level.setText( textLevel );
        int diamond=Hawk.get("diamond");
        String diamondSt=""+diamond;
        text_diamond.setText( ""+diamond );
        String avatarId=Hawk.get("avatarId");
        Glide.with(getActivity()).load(avatarId).into(circleImageUser);
        //Glide.with(getActivity()).load("http://moonishop.ir/nardeban/images/iconfinder_41.png").into(circleImageUser);

        //circleImageUser.setImageResource( avatarId );

    }

    @Override
    public void onPause() {
        super.onPause();
        timerhome.cancel();
    }

    //******************************Run timer & Set menu icon ****************************
    public void timeRun(){

        timerhome=new Timer(  );
        timerhome.schedule( new TimerTask() {
            @Override
            public void run() {

                nowtime = System.currentTimeMillis();
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {

                        if ((nowtime >= timewaite) ) {

                            //Toast.makeText( getContext(),"true",Toast.LENGTH_SHORT ).show();
                            imageFilm.setImageResource( R.drawable.active_film );
                            enableFilm = true;
                        } else if ((nowtime < timewaite)  ) {
                            imageFilm.setImageResource( R.drawable.inactive_film );
                            // Toast.makeText( getContext(),"false",Toast.LENGTH_SHORT ).show();
                            enableFilm = false;
                        }


                    }
                } );

            }
        },0,2000 );

    }

//******** TouchListener for chalange images *******************************************************
    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                boolean down,up;
                String direction;
                switch(event.getAction()) {
                    case(MotionEvent.ACTION_DOWN):
                        x1 = event.getX();

                        break;
                    case(MotionEvent.ACTION_UP):
                        x2 = event.getX();

                       break;
                }
                if (x1!=0 && x2!=0){
                    dx = x2 - x1;

                    if (Math.abs( dx ) > 120) {
                        if (dx > 0)
                            direction = "right";
                        else
                            direction = "left";
                    } else if (Math.abs( dx ) < 10){
                        direction = "click";
                    }
                    else  direction = "not";

                    x1=0;
                    x2=0;
                    switch (direction){
                        case "right":
                            if (ok==true) {
                                setButtonBack();
                                new Handler().postDelayed( new Runnable() {

                                    @Override
                                    public void run() {
                                        ok=true;

                                    }
                                } , 400 ) ;
                            }
                            break;
                        case "left":

                            if (ok==true) {
                                setButtonForward();
                                new Handler().postDelayed( new Runnable() {

                                    @Override
                                    public void run() {
                                        ok=true;

                                    }
                                } , 400 ) ;
                            }
                            break;
                        case "click":
                          //  Toast.makeText( getContext(),direction,Toast.LENGTH_LONG ).show();

                            if(state==1){

                                Animation anim = new ScaleAnimation(
                                        .95f, 1f,
                                        .95f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0f,
                                        Animation.RELATIVE_TO_SELF, 1f);
                                anim.setFillAfter(true);
                                img_chance_challenge.startAnimation(anim);
                                startActivity( new Intent( getContext(), ChanceChalengeMachActivity.class ) );

                                getActivity().finish();
                                
                            }
                            if(state==2){

                                Animation anim = new ScaleAnimation(
                                        .95f, 1f,
                                        .95f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0f,
                                        Animation.RELATIVE_TO_SELF, 1f);
                                anim.setFillAfter(true);
                                img_friendly_challenge.startAnimation(anim);

                                startActivity( new Intent( getContext(), VSchance.class ) );
                                getActivity().finish();

                            }
                            if(state==3){

                                Animation anim = new ScaleAnimation(
                                        .95f, 1f,
                                        .95f, 1f,
                                        Animation.RELATIVE_TO_SELF, 0f,
                                        Animation.RELATIVE_TO_SELF, 1f);
                                anim.setFillAfter(true);
                                img_friendly_challenge.startAnimation(anim);

                                startActivity( new Intent( getContext(), SelectLessonStudyActivity.class ) );
                                getActivity().finish();

                            }
                            // startActivity( new Intent(  ) );
                            break;

                    }


                }


                return true;
            }
        };
    }


}




