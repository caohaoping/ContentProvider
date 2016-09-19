package com.example.sqlitedemo.test;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.sqlitedemo.dao.PersonDao;
import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;
import com.example.sqlitedemo.entities.Person;

public class TestCase extends AndroidTestCase
{
	private String TAG = "PersonDao";

	public void test()
	{
		//数据库什么时候创建
		PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
		//第一次连接数据库时创建数据库文件，onCreate会被调用
		openHelper.getWritableDatabase();
	}
	
	public void testInsert()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.insert(new Person(1, "李四", 22));
		dao.insert(new Person(2, "王五", 25));
		dao.insert(new Person(3, "浩平", 21));
	}
	
	public void testDelete()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.delete(1);
	}
	
	public void testUptate()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.update("凤姐", 3);
	}
	
	public void testQueryAll()
	{
		PersonDao dao = new PersonDao(getContext());
		
		List<Person> personList = dao.queryAll();
		
		for(Person person : personList)
		{
			Log.i(TAG, person.toString());
		}
	}
	
	public void testQueryItem()
	{
		PersonDao dao = new PersonDao(getContext());
		
		Person person = dao.queryItem(3);
		
		Log.i(TAG, person.toString());
	}
	
	public void testTransaction()
	{
		PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		if(db.isOpen())
		{
			try
			{
				//开启事务
				db.beginTransaction();
				
				// 从张三银行账户扣1000元
				db.execSQL("update person set banlance = banlance - 1000 where name ='lisi';");
				
				//ATM机挂掉了
				@SuppressWarnings("unused")
				int result = 10/0;
				
				// 向李四账户加1000元
				db.execSQL("update person set banlance = banlance + 1000 where name = 'wangwu'");
				
				// 标记事务成功
				db.setTransactionSuccessful();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				//停止事务
				db.endTransaction();
			}
			db.close();
		}
	}
	
	public void testTransactionInsert()
	{
		PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		if(db.isOpen())
		{
			//记住当前的时间
			long start = System.currentTimeMillis();
			
			try
			{
				//开始事务
				db.beginTransaction();
				//插入数据
				for(int i = 0; i < 10000; i++)
				{
					//db.execSQL("insert into person(name, age, banlance) values('wang" + i + " ', " + (10 + i) + ", " + (10000 + i) + ")");
					db.execSQL("insert into person(name, age, banlance) values('wang', 10 + ?, 10000 + ?);", new Object[]{i, i});
				}
				//标记事务结束
				db.setTransactionSuccessful();
			}
			finally
			{
				//结束事务
				db.endTransaction();
			}
			//记住结束的时间
			long end = System.currentTimeMillis();
			
			Log.i("用时", String.valueOf(end - start));
			
			db.close();
		}
	}
}