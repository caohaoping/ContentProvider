package com.example.sqlitedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类，用于创建和管理数据库
 * @author haopi
 */
public class PersonSQLiteOpenHelper extends SQLiteOpenHelper
{

	/**
	 * 数据库的构造函数
	 * @param context
	 * 
	 * name 数据库的名称
	 * factory 游标工厂
	 * version 数据库的版本号不可以小于1
	 */
	public PersonSQLiteOpenHelper(Context context)
	{
		super(context, "info.db", null, 2);
	}

	/**
	 * 数据库第一次创建时回调此方法
	 * 初始化一些表
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// 操作数据库
		String sql = "create table person(_id integer primary key, name varchar(20), age integer);";
		// 创建person表
		db.execSQL(sql);
	}

	/**
	 * 数据库的版本号更新时回调此方法
	 * 更新数据库的内容（删除表，添加表，修改表）
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
