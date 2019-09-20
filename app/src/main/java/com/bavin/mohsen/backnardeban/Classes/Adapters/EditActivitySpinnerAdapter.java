package com.bavin.mohsen.backnardeban.Classes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;

public class EditActivitySpinnerAdapter extends BaseAdapter {
    private Context context;
   // private int[] eduFlagIds;
    private String[] eduName;
    private LayoutInflater inflater;

    public EditActivitySpinnerAdapter(Context context , String[] eduName){

        this.context=context;
        //this.eduFlagIds=eduFlagIds;
        this.eduName=eduName;
        inflater= LayoutInflater.from( context );



    }
    @Override
    public int getCount() {
        return eduName.length;
    }

    @Override
    public Object getItem(int position) {
        return eduName[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      //  View view=inflater.inflate( R.layout.spinner_educate_layout_item ,parent,false);

        ViewHolder holder;
        if(convertView==null){

            convertView=inflater.inflate( R.layout.spinner_edit_activity_layout_item ,parent,false);
            holder=new ViewHolder();
            holder.textEdu=(TextView)convertView.findViewById( R.id.text_edit_spinner_item );
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();

        }
        holder.fill( position );
        return convertView;
    }
    public class ViewHolder{
        public TextView textEdu;

        public void fill (int position){
            textEdu.setText( eduName[position] );

        }
    }
}
