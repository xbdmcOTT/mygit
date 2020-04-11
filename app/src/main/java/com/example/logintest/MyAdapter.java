package com.example.logintest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<song>list;
    public MyAdapter(MusicPlayer musicActivity, List<song> list){
        this.context=musicActivity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate( context, R.layout.item,null);
            viewHolder.song=(TextView)convertView.findViewById(R.id.item_mymusic_song);
            viewHolder.singer=(TextView)convertView.findViewById(R.id.item_mymusic_singer);
            viewHolder.duration=(TextView)convertView.findViewById(R.id.item_mymusic_duration);
            viewHolder.position=(TextView)convertView.findViewById(R.id.item_mymusic_postion);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.song.setText(list.get(position).Song.toString());
        viewHolder.singer.setText(list.get(position).singer.toString());
        int duration =list.get(position).duration;
        String time = MusicUtils.formaTime(duration);
        viewHolder.duration.setText(time);
        viewHolder.position.setText(position+1+"");
        return convertView;

    }
    class ViewHolder{
        TextView song;
        TextView singer;
        TextView duration;
        TextView position;
    }
}
