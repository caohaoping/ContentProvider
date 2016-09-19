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
		//���ݿ�ʲôʱ�򴴽�
		PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
		//��һ���������ݿ�ʱ�������ݿ��ļ���onCreate�ᱻ����
		openHelper.getWritableDatabase();
	}
	
	public void testInsert()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.insert(new Person(1, "����", 22));
		dao.insert(new Person(2, "����", 25));
		dao.insert(new Person(3, "��ƽ", 21));
	}
	
	public void testDelete()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.delete(1);
	}
	
	public void testUptate()
	{
		PersonDao dao = new PersonDao(getContext());
		
		dao.update("���", 3);
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
				//��������
				db.beginTransaction();
				
				// �����������˻���1000Ԫ
				db.execSQL("update person set banlance = banlance - 1000 where name ='lisi';");
				
				//ATM���ҵ���
				@SuppressWarnings("unused")
				int result = 10/0;
				
				// �������˻���1000Ԫ
				db.execSQL("update person set banlance = banlance + 1000 where name = 'wangwu'");
				
				// �������ɹ�
				db.setTransactionSuccessful();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				//ֹͣ����
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
			//��ס��ǰ��ʱ��
			long start = System.currentTimeMillis();
			
			try
			{
				//��ʼ����
				db.beginTransaction();
				//��������
				for(int i = 0; i < 10000; i++)
				{
					//db.execSQL("insert into person(name, age, banlance) values('wang" + i + " ', " + (10 + i) + ", " + (10000 + i) + ")");
					db.execSQL("insert into person(name, age, banlance) values('wang', 10 + ?, 10000 + ?);", new Object[]{i, i});
				}
				//����������
				db.setTransactionSuccessful();
			}
			finally
			{
				//��������
				db.endTransaction();
			}
			//��ס������ʱ��
			long end = System.currentTimeMillis();
			
			Log.i("��ʱ", String.valueOf(end - start));
			
			db.close();
		}
	}
}