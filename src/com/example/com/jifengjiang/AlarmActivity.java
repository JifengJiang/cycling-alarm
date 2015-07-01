package com.example.com.jifengjiang;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.net.Uri;

public class AlarmActivity extends Activity {
	 private Ringtone r;
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	        case 1:// 拍照
	            if (resultCode == RESULT_OK) {
	            	r.stop();
	                Toast.makeText(this, "Photographs taken successfully!", Toast.LENGTH_SHORT).show();
	                finish();
	            }
	            break;
	        default:
	            break;
	        }
	    }
	
	    protected void letCamera() {
	        // TODO Auto-generated method stub
	        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        String strImgPath = Environment.getExternalStorageDirectory().toString() + "/dlion/";// 存放照片的文件夹
	        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";// 照片命名
	        File out = new File(strImgPath);
	        if (!out.exists()) {
	            out.mkdirs();
	        }
	        out = new File(strImgPath, fileName);
	        strImgPath = strImgPath + fileName;// 该照片的绝对路径
	        Uri uri = Uri.fromFile(out);
	        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	        startActivityForResult(imageCaptureIntent, 1);
	    }
	    protected void ring(){
	    	do{
	    		r.play();
	    	}while(!r.isPlaying());
	    }
	  @Override
	  protected void onCreate(Bundle savedInstanceState)
	  {	
	    super.onCreate(savedInstanceState); /* 跳出的闹铃警示 */
	    final Vibrator v = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
	    long[] pattern = {0, 500, 1000};
        v.vibrate(pattern,-1);  
        r = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/AudioRecorder/jifengjiang.wav"));
        ring();
        
	    new AlertDialog.Builder(AlarmActivity.this).setIcon(R.drawable.wakeup)
	    
	        .setTitle("The alarm is ringing!!").setMessage("Get up!!!").
	        setNegativeButton("I would like to sleep more!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
					long delay=15000;
					new Handler().postDelayed(new Runnable(){  
					    public void run() {  
					    //execute the task 
					    	//v.cancel();	
					  		Intent intent = new Intent();
							intent.setClass(AlarmActivity.this,AlarmActivity.class);
							startActivity(intent);
					    }
					    }, delay);     
				}  			
	        		}).setPositiveButton(
	            "Close it? Are you sure?", new DialogInterface.OnClickListener()
	            {
	              	
	              public void onClick(DialogInterface dialog,
	                  int whichButton)
	              {
	            	letCamera();
	              }
	            }).show();
	            
	    
    }
}
