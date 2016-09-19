package com.example.sqlitedemo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;
import com.example.sqlitedemo.entities.Person;

/**
 * 存储业务方法：
 * 增删改查数据库
 * @author haopi
 */
public class PersonDao2
{
	// 数据库的帮助类对象
	private PersonSQLiteOpenHelper mOpenHelper;

	public PersonDao2(Context context)
	{
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
			ContentValues values = new ContentValues();
			values.put("name", person.getName());
			values.put("age", person.getAge());
//			long rowId = db.insert("person", "name", null); //Person [id=11, name=null, age=0]
			long rowId = db.insert("person", "name", values);
			Log.i("insert-rowId", "插入了第" + rowId + "行");
			// 关闭数据库
			db.close();
		}
	}
	
	public void delete(int id)
	{
		// 获得可写的数据库对象
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// 如果数据库打开，执行删除的操作
		if(db.isOpen())
		{
			// 删除条件
			String whereClause = "_id = ?"; 
			//删除条件参数
			String[] whereArgs = {String.valueOf(id)};
			int number = db.delete("person", whereClause, whereArgs);
			Log.i("delete-number", "删除了" + number + "行");
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
			// 实例化内容值
			ContentValues values = new ContentValues();
			// 在values中添加内容
			values.put("name", name);
			// 修改条件
			String whereClause = "_id = ?";
			// 修改条件参数
			String[] whereArgs = {String.valueOf(id)};
			int number = db.update("person", values, whereClause, whereArgs);
			Log.i("update-number", "更新了" + number + "行");
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
			/**
			 * ①table:表名称
			 * ②columns:列名称数组
			 * ③selection:条件字句，相当于where
			 * ④selectionArgs:条件字句，参数数组
			 * ⑤groupBy:分组列
			 * ⑥having:分组条件
			 * ⑦orderBy:排序列
			 * ⑧limit:分页查询限制
			 * ⑨Cursor:返回值，相当于结果集ResultSet
			 */
			//查询获得游标
			Cursor cursor = db.query("person", null, null, null, null, null, null);
			List<Person> personList = new ArrayList<Person>();
			int id;
			String name;
			int age;
			while(cursor.moveToNext())
			{
				id = cursor.getInt(cursor.getColumnIndex("_id"));
				name = cursor.getString(cursor.getColumnIndex("name"));
				age = cursor.getInt(cursor.getColumnIndex("age"));
				
				personList.add(new Person(id, name, age));
			}
			// 关闭数据库
			db.close();
			return personList;
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
			/**
			 * ①table:表名称
			 * ②columns:列名称数组
			 * ③selection:条件字句，相当于where
			 * ④selectionArgs:条件字句，参数数组
			 * ⑤groupBy:分组列，分组语句
			 * ⑥having:分组条件，过滤语句
			 * ⑦orderBy:排序列，排序
			 * ⑧limit:分页查询限制
			 * ⑨Cursor:返回值，相当于结果集ResultSet
			 */
			// 查询获得游标
			Cursor cursor = db.query("person", new String[]{"_id", "name", "age"}, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
			
			while(cursor.moveToNext())
			{
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int age = cursor.getInt(cursor.getColumnIndex("age"));
				
				// 关闭数据库
				db.close();
				return new Person(_id, name, age);
			}
			// 关闭数据库
			db.close();
		}
		return null;
	}
}
