package com.bavin.mohsen.backnardeban.starterActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

public class SignUpFieldStudy extends AppCompatActivity {
private Spinner spinnerFieldStudy,spinnerEducationalLevel,spinnerEducationalZone;
    private AutoCompleteTextView textState;
    Button buttonState;
//private TextView text_spinner_field,text_spinner_state,text_spinner_zone;
private Button buttonNextStudy;
private boolean levelIsOk=false,fieldIsOk=false,stateIsOk=false,zoneIsOk=false;
private String[] level={"مقطع تحصیلی...","اول دبیرستان","دوم دبیرستان","سوم دبیرستان","چهارم دبیرستان"};
private String[] field={"رشته تحصیلی...","ریاضی فیزیک","علوم تجربی","علوم انسانی"};
private String[] zone={"منطقه تحصیلی...","منطقه1 ","منطقه2","منطقه3"};
private static String stateName;
private String[] state_array;
boolean contains = false;
private String levelSelect,fieldSelect,zoneSelect;
private String levelSelect2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up_field_study );
       // getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setWindowAnimations(0);
        Hawk.init(this).build();



        spinnerFieldStudy=findViewById( R.id.spinner_FieldStudy );
        spinnerEducationalLevel=findViewById( R.id.spinner_EducationalLevel );
        buttonNextStudy=findViewById( R.id.buttonNextforStudy );
        //text_spinner_field=findViewById( R.id.text_spinner_field );
      //  text_spinner_state=findViewById( R.id.text_spinner_state );
        //text_spinner_level=findViewById( R.id.text_spinner_level );
        spinnerEducationalZone=findViewById( R.id.spinner_zone );
       // text_spinner_zone=findViewById( R.id.text_spinner_zone );
        buttonState =  findViewById(R.id.autocomplete_state);





       /* text_spinner_state.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( SignUpFieldStudy.this,SearchState.class ) );

            }
        } );*/

//**********************************

        //******** AutoCompleteTextView methods *************//
        /*
        state_array = getResources().getStringArray(R.array.state_array);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, state_array);
        textState.setAdapter(adapter);
        textState.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stateName= textState.getText().toString();
                if (contains = Arrays.asList(state_array).contains(stateName))
                    stateIsOk=true;
                else stateIsOk=false;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


        textState.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textState.setText( "" );
                }

            }
        } );
*/
        buttonState.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( SignUpFieldStudy.this,SearchState.class ) );
            }
        } );

        //****************************************************
        //**************************************************
        ArrayAdapter<String> adapterlevel = new ArrayAdapter<String>
                (this,  android.R.layout.simple_dropdown_item_1line,level);
        //adapterlevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEducationalLevel.setAdapter(adapterlevel);
        spinnerEducationalLevel.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText( SignUpFieldStudy.this,(String)parent.getSelectedItem(),Toast.LENGTH_LONG).show();
                int levelPosition=position;
                 levelSelect=(String)parent.getSelectedItem();
                Hawk.put("levelSelect",levelSelect);

                    switch (levelSelect) {
                        case "مقطع تحصیلی...":
                            levelSelect2="مقطع تحصیلی...";
                            Hawk.put("level",levelSelect2);
                            //text_spinner_level.setText( levelSelect );
                            levelIsOk=false;
                            break;
                        case "اول دبیرستان":
                            levelSelect2="اول";
                            Hawk.put("level",levelSelect2);
                           // text_spinner_level.setText( levelSelect );
                            levelIsOk=true;
                            break;
                        case "دوم دبیرستان":
                            levelSelect2="دوم";
                            Hawk.put("level",levelSelect2);
                           // text_spinner_level.setText( levelSelect );
                            levelIsOk=true;
                            break;
                        case "سوم دبیرستان":
                            levelSelect2="سوم";
                            Hawk.put("level",levelSelect2);
                           // text_spinner_level.setText( levelSelect );
                            levelIsOk=true;
                            break;
                        case "چهارم دبیرستان":
                            levelSelect2="چهارم";
                            Hawk.put("level",levelSelect2);
                            //text_spinner_level.setText( levelSelect );
                            levelIsOk=true;
                            break;

                    }
                checkButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
//**********************************************************************************************************

        ArrayAdapter<String> adapterField = new ArrayAdapter<String>
                (this,  android.R.layout.simple_dropdown_item_1line,field);
        //adapterField.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFieldStudy.setAdapter(adapterField);

        spinnerFieldStudy.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText( SignUpFieldStudy.this,(String)parent.getSelectedItem(),Toast.LENGTH_LONG).show();
                int fieldPosition=position;
                if (fieldPosition!=0){
                    fieldSelect=(String)parent.getSelectedItem();

                    //text_spinner_field.setVisibility( View.GONE );
                    //text_spinner_field.setText( fieldSelect );
                    fieldIsOk=true;
                    Hawk.put("field",fieldSelect );

                }
                else {
                    //text_spinner_field.setVisibility( View.VISIBLE );
                   // text_spinner_field.setText( "رشته تحصیلی..." );
                    Hawk.delete("field");
                    fieldIsOk=false;
                }

                checkButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

//***********************************************************************************************************
        ArrayAdapter<String> adapterZone = new ArrayAdapter<String>
                (this,  android.R.layout.simple_dropdown_item_1line,zone);
        //adapterlevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEducationalZone.setAdapter(adapterZone);
        spinnerEducationalZone.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText( SignUpFieldStudy.this,(String)parent.getSelectedItem(),Toast.LENGTH_LONG).show();
                int fieldPosition=position;

                if (fieldPosition!=0){
                    zoneSelect=(String)parent.getSelectedItem();
                    //text_spinner_field.setVisibility( View.GONE );
                   // text_spinner_zone.setText( zoneSelect );
                    Hawk.put("zone",zoneSelect );
                    zoneIsOk=true;
                }
                else {
                    //text_spinner_field.setVisibility( View.VISIBLE );
                   // text_spinner_zone.setText( "منطقه تحصیلی..." );
                    Hawk.delete("zone");
                    zoneIsOk=false;
                }

                checkButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        //******************************
        buttonNextStudy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldIsOk==true && levelIsOk==true && stateIsOk==true && zoneIsOk==true){
                    Intent intent = new Intent(SignUpFieldStudy.this, SignUpName.class);
                    startActivity(intent);
                    finish();

                    //startActivity(new Intent(SignUpFieldStudy.this,SignUpName.class  ) );
                }
            }
        } );
//************************************************************************************************//
        Intent intent = getIntent();
        String statement = intent.getStringExtra("statement");
        if (statement.equals( "yes" )) {
            if(Hawk.contains( "state" )){
                String state=Hawk.get( "state" );
                buttonState.setText(state);
                stateIsOk=true;
            }
            else stateIsOk=false;


            if(Hawk.contains( "levelSelect")){
                String st=Hawk.get( "levelSelect" );
                spinnerEducationalLevel.setSelection(adapterlevel.getPosition(st));
                Toast.makeText( SignUpFieldStudy.this,st,Toast.LENGTH_LONG ).show();
                levelIsOk = true;
              //  text_spinner_level.setText( st );
            }
            else levelIsOk = false;

            if(Hawk.contains( "field" )){
                String st=Hawk.get( "field" );
                spinnerFieldStudy.setSelection(adapterField.getPosition(st));
                fieldIsOk = true;
               // text_spinner_field.setText( st );
            }
            else fieldIsOk = false;


            if(Hawk.contains( "zone" )){
                String st=Hawk.get( "zone" );
                spinnerEducationalZone.setSelection(adapterZone.getPosition(st));
                zoneIsOk = true;
              //  text_spinner_zone.setText( st );
            }
            else zoneIsOk = false;

        }
        else stateIsOk=false;

        checkButtonState();

    }



//**********************************************************************************************************


    //**********************************************************************

    public void checkButtonState(){
        if (fieldIsOk==true && levelIsOk==true && stateIsOk==true && zoneIsOk==true){
            buttonNextStudy.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
        }
        else {buttonNextStudy.setBackground( getResources().getDrawable(R.drawable.edit_text_shape) );}

    }

    private void checkYes()
    {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( SignUpFieldStudy.this,RequestPassword.class ) );
        finish();
    }
}
