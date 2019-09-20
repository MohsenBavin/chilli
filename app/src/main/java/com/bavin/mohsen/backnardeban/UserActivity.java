package com.bavin.mohsen.backnardeban;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Adapters.EditActivitySpinnerAdapter;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetRegisterLoginDataRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetUpdateLoginDataRetro;
import com.bavin.mohsen.backnardeban.starterActivities.AvatarsActivity;
import com.bavin.mohsen.backnardeban.starterActivities.SignUpName;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserActivity extends AppCompatActivity {
    private ImageView chartImage,image_edit_avatar_user,
            image_edit_pluse_Avatar_user,image_edit_panel,image_cancel_edit,image_cup,image_star;
    private TextView text_edit_username,text_level_edit,text_zone_edid,text_state_edit,text_field_edit;
    private ConstraintLayout constraintLayout_edit;
    private EditText edit_username_edit;
    private Spinner spinner_level_edit,spinner_zone_edid,spinner_field_edit;
    private AutoCompleteTextView auto_text_state_edit;

    private  boolean levelIsOk=false,fieldIsOk=false,stateIsOk=false,zoneIsOk=false,contains,enterNameIsOk;
    private String[] level={"مقطع تحصیلی...","اول دبیرستان","دوم دبیرستان","سوم دبیرستان","چهارم دبیرستان"};
    private String[] field={"رشته تحصیلی...","ریاضی فیزیک","علوم تجربی","علوم انسانی"};
    private String[] zone={"منطقه تحصیلی...","منطقه1 ","منطقه2","منطقه3"};
    private static String levelSelect,fieldSelect,zoneSelect,stateName,nameEdit;
    private static String levelSelect2;
    private String[] state_array;

    private static boolean permissionEdit =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
        Hawk.init(this).build();
        getWindow().setWindowAnimations(0);


        chartImage=findViewById( R.id.image_chart );
        image_edit_avatar_user=findViewById( R.id.image_edit_avatar_user );
        image_edit_pluse_Avatar_user=findViewById( R.id.image_edit_pluse_Avatar_user );
        image_edit_panel=findViewById( R.id.image_edit_panel );
        image_cup=findViewById( R.id.image_cup );
        image_star=findViewById( R.id.image_star );
        image_cancel_edit=findViewById( R.id.image_cancel_edit );

        text_edit_username=findViewById( R.id.text_edit_username );
        text_level_edit=findViewById( R.id.text_level_edit );
        text_zone_edid=findViewById( R.id.text_zone_edid );
        text_state_edit=findViewById( R.id.text_state_edit );
        text_field_edit=findViewById( R.id.text_field_edit );

        edit_username_edit=findViewById( R.id.edit_username_edit );

        spinner_level_edit=findViewById( R.id.spinner_level_edit );
        spinner_zone_edid=findViewById( R.id.spinner_zone_edid );
        spinner_field_edit=findViewById( R.id.spinner_field_edit );

        auto_text_state_edit=findViewById( R.id.auto_text_state_edit );
        constraintLayout_edit=findViewById( R.id.constraintLayout5 );



//************ gone And set Visible ****************************************************************

        checkEditPermission();



        constraintLayout_edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } );
//*********** view Listeners ******************************
        image_edit_avatar_user.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissionEdit ) {
                    Animation anim = new ScaleAnimation(
                            .97f, 1f,
                            .97f, 1f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1f);
                    anim.setFillAfter(true);
                    v.startAnimation(anim);

                    Intent intent=new Intent( UserActivity.this, AvatarsActivity.class  );
                    intent.putExtra( "activity","UserActivity" );
                    startActivity(  intent );
                }

                }
        } );
        image_edit_pluse_Avatar_user.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissionEdit ) {
                    Animation anim = new ScaleAnimation(
                            .97f, 1f,
                            .97f, 1f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1f);
                    anim.setFillAfter(true);
                    v.startAnimation(anim);
                    Intent intent=new Intent( UserActivity.this, AvatarsActivity.class  );
                    intent.putExtra( "activity","UserActivity" );
                    startActivity(  intent );
                }

            }
        } );
//************************************
chartImage.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Animation anim = new ScaleAnimation(
                .97f, 1f,
                .97f, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true);
        v.startAnimation(anim);
        startActivity( new Intent( UserActivity.this,AnalyzeInformation.class ) );
        finish();
    }
} );
//*************************************
        image_star.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = new ScaleAnimation(
                        .97f, 1f,
                        .97f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);
                anim.setFillAfter(true);
                v.startAnimation(anim);
                startActivity( new Intent( UserActivity.this, RatingBarActivity.class ) );
                finish();
            }
        } );



image_edit_panel.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Animation anim = new ScaleAnimation(
                .97f, 1f,
                .97f, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true);
        v.startAnimation(anim);
        if (permissionEdit ==false){

            permissionEdit=true;
            checkEditPermission();

        }
        else {
            if (fieldIsOk==true && levelIsOk==true && stateIsOk==true
             && zoneIsOk==true && enterNameIsOk==true){

                Hawk.put("levelSelect",levelSelect);
                Hawk.put("level",levelSelect2);
                Hawk.put("field",fieldSelect );
                Hawk.put("zone",zoneSelect );
                Hawk.put("state", stateName);
                Hawk.put("username", nameEdit);

                if(Hawk.contains( "levelSelect3"))  Hawk.delete("levelSelect3");
                if(Hawk.contains( "field3")) Hawk.delete("field3");
                if(Hawk.contains( "zone3"))  Hawk.delete("zone3");
                if(Hawk.contains( "state3"))  Hawk.delete("state3");
                if(Hawk.contains( "username3")) Hawk.delete("username3");
                updateUserData();
                permissionEdit=false;
                checkEditPermission();


            }
            else {
                Toasty.error( UserActivity.this, "لطفا اطلاعات را درست وارد فرمایید", Toast.LENGTH_SHORT, true ).show();

            }
        }
    }
} );

    }
//**************************************************************************************************

    public void updateUserData(){
       // Toast.makeText( UserActivity.this,"updateUserData",Toast.LENGTH_LONG ).show();
        String phone=Hawk.get("phone");
        String name=Hawk.get("username");
        String level=Hawk.get("level");
        String st=Hawk.get( "levelSelect" );
        String field=Hawk.get( "field" );
        String state=Hawk.get( "state" );
        String zone=Hawk.get( "zone" );
        String avatar=Hawk.get("avatarId");
        ApiIntarfaceRetro apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<GetUpdateLoginDataRetro> regCall=apiIntarface.updateUserData
                ( phone,name,level,field,avatar,zone,state );
        regCall.enqueue( new Callback<GetUpdateLoginDataRetro>() {
            @Override
            public void onResponse(Call<GetUpdateLoginDataRetro> call, Response<GetUpdateLoginDataRetro> response) {
                String answer=response.body().getApiAnswer();
                Toast.makeText( UserActivity.this,answer,Toast.LENGTH_LONG ).show();
                if (answer.equals( "successfully" )){

                }else if (answer.equals( "error" )){
                    // updateUserData();
                }
            }

            @Override
            public void onFailure(Call<GetUpdateLoginDataRetro> call, Throwable t) {

            }
        } );

    }

//************* checkEditPermission() **************************************************************
    private void checkEditPermission() {
        if(permissionEdit){
            image_edit_panel.setImageResource(R.drawable.ok_icon);
           // constraintLayout_edit.setBackgroundColor( getResources().getColor( R.color.colorBlueDark ) );

            text_edit_username.setVisibility(View.GONE);
            text_level_edit.setVisibility(View.GONE);
            text_state_edit.setVisibility(View.GONE);
            text_zone_edid.setVisibility(View.GONE);
            text_field_edit.setVisibility(View.GONE);
            image_cup.setVisibility(View.GONE);
            image_star.setVisibility(View.GONE);
            chartImage.setVisibility(View.GONE);

            edit_username_edit.setVisibility(View.VISIBLE);
            spinner_field_edit.setVisibility(View.VISIBLE);
            spinner_level_edit.setVisibility(View.VISIBLE);
            spinner_zone_edid.setVisibility(View.VISIBLE);
            image_edit_pluse_Avatar_user.setVisibility(View.VISIBLE);
            image_cancel_edit.setVisibility(View.VISIBLE);
            auto_text_state_edit.setVisibility(View.VISIBLE);
            editMethods ();

        }
        else{
            image_edit_panel.setImageResource(R.drawable.pen);
            //constraintLayout_edit.setBackgroundColor( getResources().getColor( R.color.colorBlueText ) );

            text_edit_username.setVisibility(View.VISIBLE);
            text_level_edit.setVisibility(View.VISIBLE);
            text_state_edit.setVisibility(View.VISIBLE);
            text_zone_edid.setVisibility(View.VISIBLE);
            text_field_edit.setVisibility(View.VISIBLE);
            image_star.setVisibility(View.VISIBLE);
            chartImage.setVisibility(View.VISIBLE);
            image_cup.setVisibility(View.VISIBLE);

            edit_username_edit.setVisibility(View.GONE);
            spinner_field_edit.setVisibility(View.GONE);
            spinner_level_edit.setVisibility(View.GONE);
            spinner_zone_edid.setVisibility(View.GONE);
            image_edit_pluse_Avatar_user.setVisibility(View.GONE);
            image_cancel_edit.setVisibility(View.GONE);
            auto_text_state_edit.setVisibility(View.GONE);

            String name=Hawk.get("username");
            text_edit_username.setText( name);
            String level=Hawk.get("levelSelect");
            text_level_edit.setText( level );
            String zone=Hawk.get("zone");
            text_zone_edid.setText( zone );
            String state=Hawk.get("state");
            text_state_edit.setText( state );
            String field=Hawk.get("field");
            text_field_edit.setText( field );
        }
       // int avatarId=Hawk.get("avatarId");
       // image_edit_avatar_user.setImageResource( avatarId );
        String avatarId=Hawk.get("avatarId");
        Glide.with(UserActivity.this).load(avatarId).into(image_edit_avatar_user);

    }
//************************** editMethods () ********************//
    public void editMethods (){

        EditActivitySpinnerAdapter adapterlevel=new EditActivitySpinnerAdapter(this,level);
        spinner_level_edit.setAdapter( adapterlevel );
        /*ArrayAdapter<String> adapterlevel = new ArrayAdapter<String>
                (this,  android.R.layout.simple_dropdown_item_1line,level);*/
        //adapterlevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level_edit.setAdapter(adapterlevel);
        spinner_level_edit.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //Toast.makeText( SignUpFieldStudy.this,(String)parent.getSelectedItem(),Toast.LENGTH_LONG).show();
                int levelPosition=position;
                levelSelect=(String)parent.getSelectedItem();
                Hawk.put("levelSelect3",levelSelect);


                switch (levelSelect) {
                    case "مقطع تحصیلی...":
                        levelSelect2="مقطع تحصیلی...";
                        levelIsOk=false;
                        break;
                    case "اول دبیرستان":
                        levelSelect2="اول";
                        levelIsOk=true;
                        break;
                    case "دوم دبیرستان":
                        levelSelect2="دوم";
                        levelIsOk=true;
                        break;
                    case "سوم دبیرستان":
                        levelSelect2="سوم";
                        levelIsOk=true;
                        break;
                    case "چهارم دبیرستان":
                        levelSelect2="چهارم";
                        levelIsOk=true;
                        break;



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );
//**********************************************************************************************************

        EditActivitySpinnerAdapter adapterField=new EditActivitySpinnerAdapter(this,field);

        //adapterField.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_field_edit.setAdapter(adapterField);

        spinner_field_edit.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                int fieldPosition=position;
                fieldSelect=(String)parent.getSelectedItem();
                Hawk.put("field3",fieldSelect );
                if (fieldPosition!=0){

                    fieldIsOk=true;
                }
                else {
                    fieldIsOk=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

//***********************************************************************************************************
        EditActivitySpinnerAdapter adapterZone=new EditActivitySpinnerAdapter(this,zone);
        spinner_zone_edid.setAdapter(adapterZone);
        spinner_zone_edid.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                int fieldPosition=position;

                zoneSelect=(String)parent.getSelectedItem();
                Hawk.put("zone3",zoneSelect );
                if (fieldPosition!=0){
                    zoneIsOk=true;
                }
                else {
                    zoneIsOk=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        image_cancel_edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permissionEdit=false;
                checkEditPermission();


            }
        } );



//***************************************************************************************************
        state_array = getResources().getStringArray(R.array.state_array);// Get the string array

// Create the adapter and set it to the AutoCompleteTextView
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, state_array);
        auto_text_state_edit.setAdapter(adapter);
        auto_text_state_edit.requestFocus();
        auto_text_state_edit.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               stateName= auto_text_state_edit.getText().toString();
                Hawk.put("state3", stateName);
                if (contains = Arrays.asList(state_array).contains(stateName))
                {stateIsOk=true;
                }


                else {
                    stateIsOk=false;}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        auto_text_state_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });


        edit_username_edit.requestFocus();

        edit_username_edit.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                nameEdit= edit_username_edit.getText().toString().trim();
                if (nameEdit.length()<4){
                    //txtErrorEntername.setText( "نام کاربری نباید کمتر از 4 کاراکتر باشد" );
                    enterNameIsOk=false;
                    Hawk.put("username3", nameEdit);
                }
                else{//txtErrorEntername.setText( "" );
                    enterNameIsOk=true;
                    Hawk.put("username3", nameEdit);}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );




        //************************
        if(Hawk.contains( "levelSelect3")){
            String st=Hawk.get( "levelSelect3" );
            int index = -1;
            for (int i=0;i<level.length;i++) {
                if (level[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_level_edit.setSelection(index);
           if (index!=0)levelIsOk = true;
        }
        else if(Hawk.contains( "levelSelect")){
            String st=Hawk.get( "levelSelect" );
            //spinner_level_edit.setSelection(adapterlevel.getPosition(st));
           // int pos=levelSelect.indexOf( st );
            int index = -1;
            for (int i=0;i<level.length;i++) {
                if (level[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_level_edit.setSelection(index);
            if (index!=0)levelIsOk = true;
        }
        else levelIsOk = false;
//***************************************
        if(Hawk.contains( "field3" )){
            String st=Hawk.get( "field3" );

            int index = -1;
            for (int i=0;i<field.length;i++) {
                if (field[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_field_edit.setSelection(index);
            if (index!=0) fieldIsOk = true;
        }
        else if(Hawk.contains( "field" )){
            String st=Hawk.get( "field" );

            int index = -1;
            for (int i=0;i<field.length;i++) {
                if (field[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_field_edit.setSelection(index);
            fieldIsOk = true;
        }
        else fieldIsOk = false;

//***************************************************
        if(Hawk.contains( "zone3" )){
            String st=Hawk.get( "zone3" );
            int index = -1;
            for (int i=0;i<zone.length;i++) {
                if (zone[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_zone_edid.setSelection(index);
            if (index!=0) zoneIsOk = true;
        }
        else if(Hawk.contains( "zone" )){
            String st=Hawk.get( "zone" );
            int index = -1;
            for (int i=0;i<zone.length;i++) {
                if (zone[i].equals( st )) {
                    index = i;
                    break;
                }
            }
            spinner_zone_edid.setSelection(index);
            zoneIsOk = true;
        }
        else zoneIsOk = false;
//********************************************
        if(Hawk.contains( "state3" )){
            String st=Hawk.get( "state3" );
            auto_text_state_edit.setText(st);
            if (contains = Arrays.asList(state_array).contains(st)) {
                stateIsOk=true;
            }
            else {
                stateIsOk=false;}
        }
        else if(Hawk.contains( "state" )){
            String st=Hawk.get( "state" );
            auto_text_state_edit.setText(st);
            if (contains = Arrays.asList(state_array).contains(st)) {
                stateIsOk=true;
            }
            else {
                stateIsOk=false;}
        }
        else stateIsOk = false;
//***********************************************
        if(Hawk.contains( "username3" )){
            String st=Hawk.get( "username3" );
            edit_username_edit.setText(st);
          if (nameEdit.length()>=4)  enterNameIsOk = true;

        }
        if(Hawk.contains( "username" )){
            String st=Hawk.get( "username" );
            edit_username_edit.setText(st);
            enterNameIsOk = true;
        }
        else enterNameIsOk = false;
//**************************************************
        InputMethodManager imm2 = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        edit_username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(constraintLayout_edit.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        if(permissionEdit){
            permissionEdit=false;
            checkEditPermission();

        }
        else {
            super.onBackPressed();
            startActivity( new Intent( UserActivity.this,MainActivity.class ) );
            finish();

        }

    }
}
