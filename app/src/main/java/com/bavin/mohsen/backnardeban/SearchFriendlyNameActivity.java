package com.bavin.mohsen.backnardeban;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.ProgressDialog;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetFriendsList;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFriendlyNameActivity extends AppCompatActivity {
private Button btn_search_friend_name,button_confirm_select;
private EditText edt_enterUserName_friend_name;
private RecyclerView recycle_friend_data;
private boolean userNameIsOk=false;
private ProgressDialog progressDialog;
private int row_index=-1;
private String selectetUser="",tokenIdUser;
 private List<GetFriendsList> friendsSearchData = new ArrayList<>();
 private List<GetFriendsList> errorSearchData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search_friendly_name );
        btn_search_friend_name=findViewById( R.id.btn_search_friend_name );
        button_confirm_select=findViewById( R.id.button_confirm_select );
        edt_enterUserName_friend_name=findViewById( R.id.edt_enterUserName_friend_name );
        recycle_friend_data=findViewById( R.id.recycle_friend_data );
        progressDialog=new ProgressDialog( SearchFriendlyNameActivity.this );

        edt_enterUserName_friend_name.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user= edt_enterUserName_friend_name.getText().toString().trim();
                if (user.length()<1){
                    userNameIsOk=false;
                }
                else{
                    userNameIsOk=true;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        btn_search_friend_name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameIsOk){
                    String userData= edt_enterUserName_friend_name.getText().toString().trim();
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    getData( userData );


                }
                else{
                    Toasty.warning( SearchFriendlyNameActivity.this,
                            "اسم دوستت رو وارد کن",Toasty.LENGTH_SHORT).show();
                }
            }
        } );

        button_confirm_select.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (row_index>=0){
                    Intent intent=new Intent( SearchFriendlyNameActivity.this,FriendlySelectLessonActivity.class );
                    intent.putExtra( "friendName",selectetUser);
                    intent.putExtra( "tokenIdUser",tokenIdUser);
                    startActivity( intent );
                    finish();
                }

            }
        } );
    }

    private void getData(String userName){

        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetFriendsList>>friendsList=apiIntarfaceRetro.getFriendsList( userName );
        friendsList.enqueue( new Callback<List<GetFriendsList>>() {
            @Override
            public void onResponse(@NotNull Call<List<GetFriendsList>> call, @NotNull Response<List<GetFriendsList>> response) {
                friendsSearchData=response.body();
                Toast.makeText( SearchFriendlyNameActivity.this,""+friendsSearchData.size(),Toast.LENGTH_SHORT ).show();

                GetFriendsListAdapter  adapter=new GetFriendsListAdapter( friendsSearchData );
                recycle_friend_data.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer =
                        new LinearLayoutManager(SearchFriendlyNameActivity.this, LinearLayoutManager.VERTICAL, false);
                recycle_friend_data.setLayoutManager(horizontalLayoutManagaer);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<GetFriendsList>> call, Throwable t) {
              //Toast.makeText( SearchFriendlyNameActivity.this,"onFailure",Toast.LENGTH_SHORT ).show();
                Toasty.error( SearchFriendlyNameActivity.this,"کاربری با این مشخصات پیدا نشد!",Toasty.LENGTH_LONG ).show();
              progressDialog.dismiss();
                GetFriendsListAdapter  adapter=new GetFriendsListAdapter( errorSearchData );
                recycle_friend_data.setAdapter(adapter);
                LinearLayoutManager horizontalLayoutManagaer =
                        new LinearLayoutManager(SearchFriendlyNameActivity.this, LinearLayoutManager.VERTICAL, false);
                recycle_friend_data.setLayoutManager(horizontalLayoutManagaer);

                progressDialog.dismiss();


            }
        } );
    }

    public class GetFriendsListAdapter extends RecyclerView.Adapter<GetFriendsListAdapter.MyFriendsHolder> {

        private List<GetFriendsList> getFriendsSearch = new ArrayList<>();

        private GetFriendsListAdapter(List<GetFriendsList> getFriendsSearch) {
            this.getFriendsSearch = getFriendsSearch;
        }

        @NotNull
        @Override
        public MyFriendsHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.item_feriend_data_search, viewGroup, false );
            return new MyFriendsHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull MyFriendsHolder myFriendsHolder, int i) {
            Log.d("myFriendsHolder", getFriendsSearch.get( i ).getApiField()+""+i);
            myFriendsHolder.img_select_friend_item.setVisibility( View.GONE );

            myFriendsHolder.text_friend_search_field.setText( getFriendsSearch.get( i ).getApiField() );
            myFriendsHolder.text_friend_search_level.setText( getFriendsSearch.get( i ).getApiLevel() );
            myFriendsHolder.text_friend_search_name.setText( getFriendsSearch.get( i ).getApiUsername() );
            String imgUrl= getFriendsSearch.get( i ).getApiAvatar();
            Glide.with(getBaseContext()).load(imgUrl).into(myFriendsHolder.circleImage_friend_search);





            myFriendsHolder.item_friend_data.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // if (clickSound) select_sound.start();
                    row_index = i;
                    button_confirm_select.setBackground( getResources().getDrawable(R.drawable.button_green_click_state ) );
                    notifyDataSetChanged();
                    selectetUser=getFriendsSearch.get( i ).getApiUsername();
                    tokenIdUser=getFriendsSearch.get( i ).getApiTokenId();
                }
            } );

            if (row_index == i) {
                myFriendsHolder.img_select_friend_item.setVisibility( View.VISIBLE );

            } else {

                myFriendsHolder.img_select_friend_item.setVisibility( View.GONE );
            }
        }

        @Override
        public int getItemCount() {
            return getFriendsSearch.size();
        }

        class MyFriendsHolder extends RecyclerView.ViewHolder {

            private TextView text_friend_search_name, text_friend_search_level, text_friend_search_field;
            private CircleImageView circleImage_friend_search;
            private ImageView img_select_friend_item;
            private ConstraintLayout item_friend_data;

            MyFriendsHolder(@NonNull View itemView) {
                super( itemView );
                text_friend_search_name = itemView.findViewById( R.id.text_friend_search_name );
                text_friend_search_level = itemView.findViewById( R.id.text_friend_search_level );
                text_friend_search_field = itemView.findViewById( R.id.text_friend_search_field );
                circleImage_friend_search = itemView.findViewById( R.id.circleImage_friend_search );
                img_select_friend_item = itemView.findViewById( R.id.img_select_friend_item );
                item_friend_data = itemView.findViewById( R.id.item_friend_data );
            }

        }
    }
}
