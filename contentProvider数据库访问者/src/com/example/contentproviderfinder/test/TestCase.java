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
		 * �������Ҫ��Android����ʵ��Ӧ�ó���֮�����ݹ����
		 * 
		 * һ���������ͨ��ʵ��һ��Content provider�ĳ���ӿڽ��Լ���������ȫ��¶��ȥ��
		 * ����Content providers�����������ݿ��б�ķ�ʽ�����ݱ�¶��
		 * Content providers�洢�ͼ������ݣ�
		 * ͨ�������������е�Ӧ�ó�����ʵ���
		 * ��Ҳ��Ӧ�ó���֮��Ψһ�������ݵķ�����
		 * ContentResolver��ͨ��URI����ѯContentProvider���ṩ������
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("name", "���");
		values.put("age", 90);
		Uri uri2;
		for(int i = 0; i < 20; i++)
		{
			uri2 = resolver.insert(uri, values);
			Log.i(TAG, "uri: " + uri2);
			
			/** 
			 * ContentUris �����ڻ�ȡUri·�������ID����
			 * 1)Ϊ·������ID: withAppendedId(uri, id)
			 * 2)��·���л�ȡID: parseId(uri)
			 */
			//ContentUris newUri = ContentUris.withAppendedId(contentUri, id);
			long id = ContentUris.parseId(uri2);
			Log.i(TAG, "��ӵ�: " + id);
		}
	}
	
	public void testDelete()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/delete");
		
		/**
		 * ContentResolver:
		 * �������Ҫ��Android����ʵ��Ӧ�ó���֮�����ݹ����
		 * 
		 * һ���������ͨ��ʵ��һ��Content provider�ĳ���ӿڽ��Լ���������ȫ��¶��ȥ��
		 * ����Content providers�����������ݿ��б�ķ�ʽ�����ݱ�¶��
		 * Content providers�洢�ͼ������ݣ�
		 * ͨ�������������е�Ӧ�ó�����ʵ���
		 * ��Ҳ��Ӧ�ó���֮��Ψһ�������ݵķ�����
		 * ContentResolver��ͨ��URI����ѯContentProvider���ṩ������
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		String where = "_id = ?";
		String[] selectionArgs = {"21"};
		
		int number = resolver.delete(uri, where, selectionArgs);
		Log.i(TAG, "ɾ����: " + number + "��");
	}
	
	public void testUpdate()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/update");

		/**
		 * ContentResolver:
		 * �������Ҫ��Android����ʵ��Ӧ�ó���֮�����ݹ����
		 * 
		 * һ���������ͨ��ʵ��һ��Content provider�ĳ���ӿڽ��Լ���������ȫ��¶��ȥ��
		 * ����Content providers�����������ݿ��б�ķ�ʽ�����ݱ�¶��
		 * Content providers�洢�ͼ������ݣ�
		 * ͨ�������������е�Ӧ�ó�����ʵ���
		 * ��Ҳ��Ӧ�ó���֮��Ψһ�������ݵķ�����
		 * ContentResolver��ͨ��URI����ѯContentProvider���ṩ������
		 */
		ContentResolver resolver = getContext().getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("name", "�ܺ�ƽ");
		String where = "_id = ?";
		String[] selectionArgs = {"40"};
		
		int number = resolver.update(uri, values, where, selectionArgs);
		Log.i(TAG, "������: " + number + "��");
	}
	
	public void testQueryAll()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/queryAll");

		/**
		 * ContentResolver:
		 * �������Ҫ��Android����ʵ��Ӧ�ó���֮�����ݹ����
		 * 
		 * һ���������ͨ��ʵ��һ��Content provider�ĳ���ӿڽ��Լ���������ȫ��¶��ȥ��
		 * ����Content providers�����������ݿ��б�ķ�ʽ�����ݱ�¶��
		 * Content providers�洢�ͼ������ݣ�
		 * ͨ�������������е�Ӧ�ó�����ʵ���
		 * ��Ҳ��Ӧ�ó���֮��Ψһ�������ݵķ�����
		 * ContentResolver��ͨ��URI����ѯContentProvider���ṩ������
		 */
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, "_id");
		
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			
			Log.i(TAG, "��ѯ��--" + " id: " + id + " name: " + name + " age: " + age );
		}
		cursor.close();
	}
	
	public void testQueryItem()
	{
		Uri uri = Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider/person/query/#");
		
		/** 
		 * ContentUris �����ڻ�ȡUri·�������ID����
		 * 1)Ϊ·������ID: withAppendedId(uri, id)
		 * 2)��·���л�ȡID: parseId(uri)
		 */
		uri =  ContentUris.withAppendedId(uri, 13);
		
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, null);
		
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			
			Log.i(TAG, "��ѯ��--" + " id: " + id + " name: " + name + " age: " + age );
		}
		cursor.close();
	}
}
