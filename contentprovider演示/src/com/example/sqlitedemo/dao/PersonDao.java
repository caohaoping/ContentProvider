package com.example.sqlitedemo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;
import com.example.sqlitedemo.entities.Person;

/**
 * 存储业务方法：
 * 增删改查数据库
 * @author haopi
 */
public class PersonDao
{
	// 数据库的帮助类对象
	private PersonSQLiteOpenHelper mOpenHelper;

	public PersonDao(Context context)
	{
		//创建数据库
		mOpenHelper = new PersonSQLiteOpenHelper(context);
		
	}
	
	/**
	 * 添加到person表一条数据
	 * @param person
	 */
	public void insert(Person person)
	{
		// 获得可写的数据库对象
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// 如果数据库打开，执行添加的操作
		if(db.isOpen())
		{
			// 执行添加到数据库的操作
			db.execSQL("insert into person(name, age) values(?, ?);", new Object[]{person.getName(), person.getAge()});
			// 关闭数据库
			db.close();
		}
	}
	
	public void delete(int id)
	{
		//获得可写的数据库对象
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// 如果数据库打开，执行删除的操作
		if(db.isOpen())
		{
			// 执行删除数据库的操作
			db.execSQL("delete from person where _id = ?;", new Integer[]{id});
			// 关闭数据库
			db.close();
		}
	}
	
	public void update(String name, int id)
	{
		// 获得可写的数据库对象
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// 如果数据库打开，执行更新的操作
		if(db.isOpen())
		{
			// 执行更新数据库的操作
			db.execSQL("update person set name = ? where _id = ?;", new Object[]{name, id});
			// 关闭数据库
			db.close();
		}
	}
	
	public List<Person> queryAll()
	{
		// 获得可读的数据库对象
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// 如果数据库打开，执行查询操作
		if(db.isOpen())
		{
			// 执行查询数据库的操作
			Cursor cursor = db.rawQuery("select _id, name, age from person;", null);
			// 如果cursor对象不为空，并且查询的条数大于0
			if(null != cursor && 0 < cursor.getCount())
			{
				List<Person> personList = new ArrayList<Person>();
				int id;
				String name;
				int age;
				// 当cursor有下一个
				while(cursor.moveToNext())
				{
					id = cursor.getInt(0);
					name = cursor.getString(1);
					age = cursor.getInt(2);
					
					personList.add(new Person(id, name, age));
				}
				// 关闭数据库
				db.close();
				return personList;
			}
			
			// 关闭数据库
			db.close();
		}
		return null;
	}
	
	public Person queryItem(int id)
	{
		// 获得可读的数据库对象
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// 如果数据库打开，执行查询的操作
		if(db.isOpen())
		{
			// 执行查询数据库的操作
			Cursor cursor = db.rawQuery("select _id, name, age from person where _id = ?;", new String[]{String.valueOf(id)});
			// 当cursor对象不为空，并且能移动到第一条（说明有查询结果）
			if(null != cursor && cursor.moveToFirst())
			{
				int _id = cursor.getInt(0);
				String name = cursor.getString(1);
				int age = cursor.getInt(2);
				Person person = new Person(_id, name, age);
				return person;
			}
			// 关闭数据库
			db.close();
		}
		return null;
	}
}
