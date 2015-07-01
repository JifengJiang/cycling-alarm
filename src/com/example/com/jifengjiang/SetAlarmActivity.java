package com.example.com.jifengjiang;

import java.util.Calendar;
import java.util.TimeZone;




import com.example.com.jifengjiang.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class SetAlarmActivity extends Activity {
	int jHOUR;
	int jMINUTE;
	
	private TimePicker tp;
	private Button button_set;
	private Button button_cancel;
	private TextView TextView;
	private Calendar calendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setalarm);
		
		TextView = (TextView)findViewById(R.id.TextView);
		
		calendar = Calendar.getInstance();	
		jHOUR=calendar.get(Calendar.HOUR_OF_DAY);
		jMINUTE=calendar.get(Calendar.MINUTE);
			
		tp = (TimePicker)findViewById(R.id.timePicker1);
		tp.setCurrentHour(jHOUR);
		tp.setCurrentMinute(jMINUTE);
		tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
				jHOUR = hourOfDay;
				jMINUTE = minute;
			}
		});
		button_set = (Button)findViewById(R.id.button_set);
		button_set.setOnClickListener(new Button.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub						
				Intent intent = new Intent(SetAlarmActivity.this,Timeup.class);		
				Toast.makeText(SetAlarmActivity.this, "     Alarm starts at:  "+timeformat(tp.getCurrentHour())+" : "+timeformat(tp.getCurrentMinute()), Toast.LENGTH_SHORT).show();
				PendingIntent pi = PendingIntent.getBroadcast(SetAlarmActivity.this, 0, intent, 0);
				
				Calendar calendar = Calendar.getInstance();
			 	calendar.setTimeInMillis(System.currentTimeMillis());
			 	calendar.set(Calendar.MINUTE, jMINUTE);
			 	calendar.set(Calendar.HOUR_OF_DAY, jHOUR);
			 	calendar.set(Calendar.SECOND, 0);
			 	calendar.set(Calendar.MILLISECOND, 0);
				
				AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
				//am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pi);	
				
				TextView.setText("You have set the time at "+timeformat(tp.getCurrentHour())+" : "+timeformat(tp.getCurrentMinute()));
			}
		}); 
		button_cancel = (Button)findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(new Button.OnClickListener(){
			 public void onClick(View v)
		      {
		    	Intent intent = new Intent(SetAlarmActivity.this, Timeup.class);
		        PendingIntent pi=PendingIntent.getBroadcast(SetAlarmActivity.this,0, intent, 0);
		        AlarmManager am;
		        am =(AlarmManager)getSystemService(ALARM_SERVICE);
		        am.cancel(pi);
		        Toast.makeText(SetAlarmActivity.this, "Alarm has been cancelled!", Toast.LENGTH_SHORT).show();
		        TextView.setText("Alarm has been cancelled!");
		        android.os.Process.killProcess(android.os.Process.myPid());
		      }
			
		});
	}
	private String timeformat(int x)
	{
		String s = x+"";
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

}

