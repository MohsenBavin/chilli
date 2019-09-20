package com.bavin.mohsen.backnardeban.starterActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetAnsupdateAvatar;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetAvatarsAddress;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetLoginDataRetro;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.UserActivity;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarsActivity extends AppCompatActivity {
    RecyclerView recycler;
    Button buttonOk;
    int row_index=-1;
   private  String statement;
    List<GetAvatarsAddress> address;
   // List<GetAvatarsAddress> address=new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_avatars );
        recycler=findViewById( R.id.recycle_avatar );
        buttonOk=findViewById( R.id.bottom_ok_avatar );

//**************************************************************************
        Intent intent = getIntent();
         statement = intent.getStringExtra("activity");
//**************************************************************************
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetAvatarsAddress>> avatarCall=apiIntarfaceRetro.showAvatarCall();
        avatarCall.enqueue( new Callback<List<GetAvatarsAddress>>() {
            @Override
            public void onResponse(Call<List<GetAvatarsAddress>> call, Response<List<GetAvatarsAddress>> response) {
                 address=response.body();
                AvatarList adapterAvatar =new AvatarList(address);
                recycler.setAdapter( adapterAvatar );
                recycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));

            }

            @Override
            public void onFailure(Call<List<GetAvatarsAddress>> call, Throwable t) {
                if (t != null) {
                    Log.e("OnFailure",""+ t.getMessage()  );
                    Toast.makeText(AvatarsActivity.this, "OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } );



//***************************************************************************
        buttonOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(row_index!= -1){
                    Hawk.put("avatarId", address.get( row_index ).getApiAvatarAddress());
                    String phone=Hawk.get( "phone" );
                    ApiIntarfaceRetro apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
                    Call<GetAnsupdateAvatar> updateAvatarCall=apiIntarface.updateAvatar
                            ( phone,address.get( row_index ).getApiAvatarAddress() );
                    updateAvatarCall.enqueue( new Callback<GetAnsupdateAvatar>() {
                        @Override
                        public void onResponse(Call<GetAnsupdateAvatar> call, Response<GetAnsupdateAvatar> response) {
                            if (statement.equals( "UserActivity" )) {
                                startActivity( new Intent( AvatarsActivity.this, UserActivity.class ) );
                                finish();
                            }
                            if (statement.equals( "SignUpName" )) {
                                startActivity( new Intent( AvatarsActivity.this, SignUpName.class ) );
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetAnsupdateAvatar> call, Throwable t) {

                        }
                    } );



                }
            }
        } );


    }




//***********AvatarList RecyclerView ******************************************

public class AvatarList extends RecyclerView.Adapter <AvatarList.MyAvatarViewHolder>{
    List<GetAvatarsAddress> address=new ArrayList<>(  );
public AvatarList(List<GetAvatarsAddress> address){
    this.address=address;

}
    @NonNull
    @Override
    public MyAvatarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from( viewGroup.getContext() )
                .inflate( R.layout.avatar_layout_item,viewGroup,false );
        MyAvatarViewHolder holder;
        holder=new MyAvatarViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAvatarViewHolder myAvatarViewHolder, final int i) {
        //myAvatarViewHolder.imageAvatar.setImageResource( idAvatarList.get( i ) );

        Glide.with(getBaseContext()).load(address.get( i ).getApiAvatarAddress()).into(myAvatarViewHolder.imageAvatar);

       myAvatarViewHolder.imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = i;
                buttonOk.setBackground( getResources().getDrawable(R.drawable.active_buttonshape ) );
                notifyDataSetChanged();

            }
        });

        if(row_index==i){
            myAvatarViewHolder.constraintAvatar.setBackgroundColor(getResources().getColor( R.color.colorBlueText ));

        }
        else
        {

            myAvatarViewHolder.constraintAvatar.setBackgroundColor(getResources().getColor( R.color.white_with_alpha ));
        }


    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class MyAvatarViewHolder extends RecyclerView.ViewHolder{
        ImageView imageAvatar;
        ConstraintLayout constraintAvatar;


        public MyAvatarViewHolder(@NonNull View itemView) {
            super( itemView );
            imageAvatar=itemView.findViewById( R.id.image_avatar_item );
            constraintAvatar=itemView.findViewById( R.id.constraint_avatar_item );


        }
    }
}

    @Override
    public void onBackPressed() {
        if (statement.equals( "UserActivity" )) {
            startActivity( new Intent( AvatarsActivity.this, UserActivity.class ) );
            finish();
        }
        if (statement.equals( "SignUpName" )) {
            startActivity( new Intent( AvatarsActivity.this, SignUpName.class ) );
            finish();
        }

    }
}
