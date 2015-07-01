package com.example.com.jifengjiang;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import com.example.com.jifengjiang.R;




import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WakeupActivity extends Activity {
	public static final String TAG = "Wakeup";
	private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
	private MediaRecorder recorder = null;
	private int output_formats = MediaRecorder.OutputFormat.THREE_GPP ;
	private String file_exts =AUDIO_RECORDER_FILE_EXT_WAV ;
	private Button button_set;
	private Button button_cancel;
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	        case 1:// 拍照
	            if (resultCode == RESULT_OK) {
	                Toast.makeText(this, "Photographs taken successfully!", Toast.LENGTH_SHORT).show();
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wakeup);
		Toast.makeText(WakeupActivity.this, "Click on the alarm to set alarm please!"+"\n"+"Click on the record to take a picture!", Toast.LENGTH_SHORT).show();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		button_set = (Button)findViewById(R.id.button1);
		button_set.setOnClickListener(new Button.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				Intent intent = new Intent();
				intent.setClass(WakeupActivity.this,SetAlarmActivity.class);
				startActivity(intent);
			}
		});
		
		button_cancel = (Button)findViewById(R.id.button2);
		button_cancel.setOnClickListener(new Button.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				letCamera();
			}
		});
		
	}
}




