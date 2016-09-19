package com.example.sqlitedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ�����࣬���ڴ����͹������ݿ�
 * @author haopi
 */
public class PersonSQLiteOpenHelper extends SQLiteOpenHelper
{

	/**
	 * ���ݿ�Ĺ��캯��
	 * @param context
	 * 
	 * name ���ݿ������
	 * factory �α깤��
	 * version ���ݿ�İ汾�Ų�����С��1
	 */
	public PersonSQLiteOpenHelper(Context context)
	{
		super(context, "info.db", null, 2);
	}

	/**
	 * ���ݿ��һ�δ���ʱ�ص��˷���
	 * ��ʼ��һЩ��
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// �������ݿ�
		String sql = "create table person(_id integer primary key, name varchar(20), age integer);";
		// ����person��
		db.execSQL(sql);
	}

	/**
	 * ���ݿ�İ汾�Ÿ���ʱ�ص��˷���
	 * �������ݿ�����ݣ�ɾ������ӱ��޸ı�
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if(1 == oldVersion && 2 == newVersion)
		{
			db.execSQL("alter table person add banlance integer;");
		}
	}
	
}
