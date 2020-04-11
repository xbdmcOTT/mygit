package com.example.logintest;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/*音乐工具类
扫描本地的音乐文件，并返回一个集合

 */
public class MusicUtils {
    /*static final Uri URI= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    static final String[] MUCSIC_PROJECTION=new String []{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };
    private static final String SELECTION ="mine_type in ('audio/mpeg','audio/x-ms-wma')and bucket_dispaly_naeme <> 'audio' and is_music >0";
    private static final String SORT_ORDER= MediaStore.Audio.Media.IS_MUSIC;*/
    public static List<song> getMusicData (Context context ){
        List<song> list=new ArrayList<song>();//创建动态的队列
        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //获取本地音乐，可更改获得网络音乐
        if(cursor!=null){
            while(cursor.moveToNext()){
                song Song =new song();
                Song .Song=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                Song .singer=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                Song .path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Song.duration=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                Song .size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                if(Song.size>1000*800){
                    //分出歌手和歌曲名（本地媒体库读出的歌曲信息不规范）
                    if(Song.Song.contains("-")){
                        String []str=Song.Song.split("-");
                        Song.singer=str[0];
                        Song.Song =str[1];
                    }
                    list.add(Song);//向列表中添加音乐
                }


            }
            cursor.close();
        }
            return list;
    }
    public static String formaTime(int time ){
        if(time/1000%60<10){
            return  time /1000/60+":0"+time /1000%60;

        }
        else
        {
            return time /1000/60+":"+time /1000%60;
        }

    }//毫秒时间转换函数


}
