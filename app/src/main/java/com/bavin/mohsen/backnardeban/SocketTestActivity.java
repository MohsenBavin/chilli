package com.bavin.mohsen.backnardeban;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Adapters.JustifiedTextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketTestActivity extends AppCompatActivity {

    TextView text_test_socket,text_test_socket2;
    Button btn_test_socket;
    EditText edit_socket;
    private Socket socket;
    {
        try {
            socket= IO.socket("http://192.168.1.100:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_socket_test );
        btn_test_socket=findViewById( R.id.btn_test_socket );
        text_test_socket=findViewById( R.id.text_test_socket );
        text_test_socket2=findViewById( R.id.text_test_socket2 );
        edit_socket=findViewById( R.id.edit_socket );


        socket.connect();
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            ReciveMessage( jsonObject.getString( "message" ),"other"  );
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } );

            }
        });
/*
    socket.emit( "message", new Emitter.Listener() {

            @Override
            public void call(Object... args) {


                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            ReciveMessage( jsonObject.getString( "message" ),null  );
                            Toast.makeText( SocketTestActivity.this,jsonObject.getString( "message" )+"get",Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } );


            }
        } );*/


        btn_test_socket.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=edit_socket.getText().toString().trim();
                ReciveMessage(text,"me");
                JSONObject sendmsg=new JSONObject();
                try {
                    sendmsg.put("message",text);
                    socket.emit("message",sendmsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );


        }


    void ReciveMessage(String msg,String checkUser){
        if (checkUser.equals( "me" )){
            text_test_socket.setText( msg );
        }
        else text_test_socket2.setText( msg );



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
