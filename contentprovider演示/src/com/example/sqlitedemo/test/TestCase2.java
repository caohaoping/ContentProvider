package com.example.sqlitedemo.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.sqlitedemo.dao.PersonDao2;
import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;
import com.example.sqlitedemo.entities.Person;

public class TestCase2 extends AndroidTestCase
{
	private String TAG = "PersonDao2";

	public void test()
	{
		//���ݿ�ʲôʱ�򴴽�
		PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());
		//��һ���������ݿ�ʱ�������ݿ��ļ���onCreate�ᱻ����
		openHelper.getWritableDatabase();
	}
	
	public void testInsert()
	{
		PersonDao2 dao = new PersonDao2(getContext());
		
		for(int i = 0; i < 30; i++)
		{
			dao.insert(new Person(1, "�Ž���", 21));
		}
	}
	
	public void testDelete()
	{
		PersonDao2 dao = new PersonDao2(getContext());
		
		dao.delete(13);
	}
	
	public void testUptate()
	{
		PersonDao2 dao = new PersonDao2(getContext());
		
		dao.update("Ϭ����", 11);
	}
	
	public void testQueryAll()
	{
		PersonDao2 dao = new PersonDao2(getContext());
		
		List<Person> personList = dao.queryAll();
		
		for(Person person : personList)
		{
			Log.i(TAG, person.toString());
		}
	}
	
	public void testQueryItem()
	{
		PersonDao2 dao = new PersonDao2(getContext());
		
		Person person = dao.queryItem(8);
		
		Log.i(TAG, person.toString());
	}
}