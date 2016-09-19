package com.example.sqlitedemo;

import com.example.sqlitedemo.R;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends Activity
{
	protected static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 注册观察者
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider");
		getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler())
		{
			@Override
			public void onChange(boolean selfChange)
			{
				Log.i(TAG, "内容改变了");
				
			}
		});
	}
}
