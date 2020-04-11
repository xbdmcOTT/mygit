package com.example.logintest;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends AppCompatActivity {
    private ListView mListView;
    private List<song> list;

    private MediaPlayer mediaPlayer;
    private ImageView pre;
    private ImageView play;
    private ImageView next;
    private ImageView go;
    private TextView nowtime;
    private TextView alltime;
    private TextView musicname;
    private SeekBar seekBar;
    private boolean IsStop;
    private int Postion;
    private  boolean isSeekingClick;
    @SuppressLint("HandlerLeak")//会造成内存泄漏注释
    private Handler handler = new Handler(){
        @Override
        public void handleMessage (@NonNull Message msg){
            super.handleMessage(msg);
            seekBar.setProgress(msg.what);
            nowtime.setText(MusicUtils.formaTime(msg.what));
        }
    };//handle实现接受消息处理消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initview();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mediaPlayer !=null&& mediaPlayer. isPlaying()){
                    mediaPlayer. stop() ;
                }
                Postion = position;
                play(list.get(position).path);

            }
        });//点击列表的响应，获取播放的地址
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    isSeekingClick=false;
                int progress =seekBar.getProgress();
                mediaPlayer.seekTo(progress);//在拉动滚动条结束后在更新歌曲当前时间
                //歌曲不会卡顿
            }
        });//监听滚动条


        next.setOnClickListener(new BTListener());
        play.setOnClickListener(new BTListener());
        pre.setOnClickListener(new BTListener());
        go.setOnClickListener(new BTListener());//按钮监听
    }

    private void initview() {
        MyAdapter Adapter;
        mListView = (ListView) findViewById(R.id.musicList);
        list = new ArrayList<>();
        list = MusicUtils.getMusicData(this);
        Adapter = new MyAdapter(this,list);
        mListView.setAdapter(Adapter);
        mediaPlayer = new MediaPlayer();
        pre = (ImageView) findViewById(R.id.preBT);
        play = (ImageView) findViewById(R.id.playBT);
        next = (ImageView) findViewById(R.id.nextBT);
        go = (ImageView) findViewById(R.id.goback);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        nowtime = (TextView) findViewById(R.id.nowtime);
        alltime = (TextView) findViewById(R.id.alltime);
        musicname = (TextView) findViewById(R.id.MusicName);
    }//绑定id

    private void play(String Path) {
        Thread MusicThread;
        IsStop = false;
        alltime.setText(MusicUtils.formaTime(list.get(Postion).duration));
        String ct = list.get(Postion).singer;
        String cv = list.get(Postion).Song;
        seekBar.setMax(list.get(Postion).duration);
        String Info = ct + "/ " + cv;
        musicname.setText(Info);//设置各种信息
        mediaPlayer.reset();
        MusicThread= new Thread(new MusicThread());
        MusicThread.start();//新建线程实现监听
        try {
            mediaPlayer.setDataSource(Path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    play.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            });//初始化MP3播放器
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//播放方法

    private class BTListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.preBT:
                    Postion--;//上一首
                    if (Postion == -1) {
                        Postion = list.size() - 1;
                    }
                    play(list.get(Postion).path);
                    break;


                case R.id.nextBT://下一首
                    Postion++;
                    if (Postion == list.size()) {
                        Postion = 0;
                    }
                    play(list.get(Postion).path);
                    break;
                case R.id.playBT://点击按钮停止播放和开始播放
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        play.setImageResource(R.drawable.play);
                    } else {
                        mediaPlayer.start();
                        play.setImageResource(R.drawable.pause);//更换图标
                    }
                    break;
                case R.id.goback:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    class MusicThread implements Runnable{

        @Override
        public void run() {
            while(mediaPlayer!=null&&!isSeekingClick){
                try{
                    Thread.sleep(100);//没100毫秒刷新一次
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());//向主线程发送消息，获取当前播放的时间
            }
        }
    }
}//线程类

