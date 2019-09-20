package com.bavin.mohsen.backnardeban.starterActivities;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SearchState extends AppCompatActivity {
   private AutoCompleteTextView textState;
   private ConstraintLayout constraintSearch;
   private Button buttonOkState;
   private static boolean okState=true;
   private static String stateName;
   private int j;
   private String names;
    boolean contains = false;
    private String[] state_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_search_state );
        textState =  findViewById(R.id.autocomplete_country);
        constraintSearch =  findViewById(R.id.constraint_search_state);
        buttonOkState =  findViewById(R.id.button_ok_state);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setWindowAnimations(0);
        Hawk.init(this).build();


//******************************** View listeners **************************************************
//******** AutoCompleteTextView methods *************//
        state_array = getResources().getStringArray(R.array.state_array);// Get the string array

// Create the adapter and set it to the AutoCompleteTextView
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, state_array);
        textState.setAdapter(adapter);
        textState.requestFocus();
        textState.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stateName= textState.getText().toString();
                if (contains = Arrays.asList(state_array).contains(stateName))
                    buttonOkState.setBackground( getResources().getDrawable(R.drawable.active_buttonshape ) );

                else buttonOkState.setBackground( getResources().getDrawable(R.drawable.inactive_button_shape ) );


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


//******** buttonOkState methods *************//
        buttonOkState.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String[] state_array = getResources().getStringArray(R.array.state_array);


               if (contains = Arrays.asList(state_array).contains(stateName))
               {
                   Hawk.put("state", stateName);
                   Intent intent = new Intent(SearchState.this, SignUpFieldStudy.class);
                   intent.putExtra( "statement","yes" );
                   startActivity(intent);
                   finish();

               }
               else Toasty.error(SearchState.this, "لطفا نام استان را درست وارد فرمایید" , Toast.LENGTH_SHORT, true).show();



            }
        } );
//******** constraintSearch methods *************//
        constraintSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintSearch.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } );

//**************************************************************************************************
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchState.this, SignUpFieldStudy.class);
        intent.putExtra( "statement","yes" );
        startActivity(intent);
        finish();
    }
}
