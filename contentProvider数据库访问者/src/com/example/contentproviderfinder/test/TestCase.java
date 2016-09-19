package com.example.contentproviderfinder.test;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestCase extends AndroidTestCase
{
	private static final String TAG = "TestCase";

	public void testInsert()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/insert");
		
		/**
		 * ContentResolver:
		 * 这个类主要是Android用来实现应用程序之间数据共享的
		 * 
		 * 一个程序可以通过实现一个Content provider的抽象接口将自己的数据完全暴露出去，
		 * 而且Content providers是以类似数据库中表的方式将数据暴露。
		 * Content providers存储和检索数据，
		 * 通过它可以让所有的应用程序访问到，
		 * 这也是应用程序之间唯一共享数据的方法。
		 * ContentResolver是通过URI来查询ContentProvider中提供的数据
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("name", "凤姐");
		values.put("age", 90);
		Uri uri2;
		for(int i = 0; i < 20; i++)
		{
			uri2 = resolver.insert(uri, values);
			Log.i(TAG, "uri: " + uri2);
			
			/** 
			 * ContentUris 类用于获取Uri路径后面的ID部分
			 * 1)为路径加上ID: withAppendedId(uri, id)
			 * 2)从路径中获取ID: parseId(uri)
			 */
			//ContentUris newUri = ContentUris.withAppendedId(contentUri, id);
			long id = ContentUris.parseId(uri2);
			Log.i(TAG, "添加到: " + id);
		}
	}
	
	public void testDelete()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/delete");
		
		/**
		 * ContentResolver:
		 * 这个类主要是Android用来实现应用程序之间数据共享的
		 * 
		 * 一个程序可以通过实现一个Content provider的抽象接口将自己的数据完全暴露出去，
		 * 而且Content providers是以类似数据库中表的方式将数据暴露。
		 * Content providers存储和检索数据，
		 * 通过它可以让所有的应用程序访问到，
		 * 这也是应用程序之间唯一共享数据的方法。
		 * ContentResolver是通过URI来查询ContentProvider中提供的数据
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		String where = "_id = ?";
		String[] selectionArgs = {"21"};
		
		int number = resolver.delete(uri, where, selectionArgs);
		Log.i(TAG, "删除了: " + number + "行");
	}
	
	public void testUpdate()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/update");

		/**
		 * ContentResolver:
		 * 这个类主要是Android用来实现应用程序之间数据共享的
		 * 
		 * 一个程序可以通过实现一个Content provider的抽象接口将自己的数据完全暴露出去，
		 * 而且Content providers是以类似数据库中表的方式将数据暴露。
		 * Content providers存储和检索数据，
		 * 通过它可以让所有的应用程序访问到，
		 * 这也是应用程序之间唯一共享数据的方法。
		 * ContentResolver是通过URI来查询ContentProvider中提供的数据
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("name", "曹浩平");
		String where = "_id = ?";
		String[] selectionArgs = {"40"};
		
		int number = resolver.update(uri, values, where, selectionArgs);
		Log.i(TAG, "更新了: " + number + "行");
	}
	
	public void testQueryAll()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/queryAll");

		/**
		 * ContentResolver:
		 * 这个类主要是Android用来实现应用程序之间数据共享的
		 * 
		 * 一个程序可以通过实现一个Content provider的抽象接口将自己的数据完全暴露出去，
		 * 而且Content providers是以类似数据库中表的方式将数据暴露。
		 * Content providers存储和检索数据，
		 * 通过它可以让所有的应用程序访问到，
		 * 这也是应用程序之间唯一共享数据的方法。
		 * ContentResolver是通过URI来查询ContentProvider中提供的数据
		 */
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, "_id");
		
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			
			Log.i(TAG, "查询到--" + " id: " + id + " name: " + name + " age: " + age );
		}
		cursor.close();
	}
	
	public void testQueryItem()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/query/#");
		
		/** 
		 * ContentUris 类用于获取Uri路径后面的ID部分
		 * 1)为路径加上ID: withAppendedId(uri, id)
		 * 2)从路径中获取ID: parseId(uri)
		 */
		uri =  ContentUris.withAppendedId(uri, 13);
		
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, null);
		
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			
			Log.i(TAG, "查询到--" + " id: " + id + " name: " + name + " age: " + age );
		}
		cursor.close();
	}
}
