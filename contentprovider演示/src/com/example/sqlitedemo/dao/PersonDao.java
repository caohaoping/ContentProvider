package com.example.sqlitedemo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;
import com.example.sqlitedemo.entities.Person;

/**
 * �洢ҵ�񷽷���
 * ��ɾ�Ĳ����ݿ�
 * @author haopi
 */
public class PersonDao
{
	// ���ݿ�İ��������
	private PersonSQLiteOpenHelper mOpenHelper;

	public PersonDao(Context context)
	{
		//�������ݿ�
		mOpenHelper = new PersonSQLiteOpenHelper(context);
		
	}
	
	/**
	 * ��ӵ�person��һ������
	 * @param person
	 */
	public void insert(Person person)
	{
		// ��ÿ�д�����ݿ����
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// ������ݿ�򿪣�ִ����ӵĲ���
		if(db.isOpen())
		{
			// ִ����ӵ����ݿ�Ĳ���
			db.execSQL("insert into person(name, age) values(?, ?);", new Object[]{person.getName(), person.getAge()});
			// �ر����ݿ�
			db.close();
		}
	}
	
	public void delete(int id)
	{
		//��ÿ�д�����ݿ����
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// ������ݿ�򿪣�ִ��ɾ���Ĳ���
		if(db.isOpen())
		{
			// ִ��ɾ�����ݿ�Ĳ���
			db.execSQL("delete from person where _id = ?;", new Integer[]{id});
			// �ر����ݿ�
			db.close();
		}
	}
	
	public void update(String name, int id)
	{
		// ��ÿ�д�����ݿ����
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// ������ݿ�򿪣�ִ�и��µĲ���
		if(db.isOpen())
		{
			// ִ�и������ݿ�Ĳ���
			db.execSQL("update person set name = ? where _id = ?;", new Object[]{name, id});
			// �ر����ݿ�
			db.close();
		}
	}
	
	public List<Person> queryAll()
	{
		// ��ÿɶ������ݿ����
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// ������ݿ�򿪣�ִ�в�ѯ����
		if(db.isOpen())
		{
			// ִ�в�ѯ���ݿ�Ĳ���
			Cursor cursor = db.rawQuery("select _id, name, age from person;", null);
			// ���cursor����Ϊ�գ����Ҳ�ѯ����������0
			if(null != cursor && 0 < cursor.getCount())
			{
				List<Person> personList = new ArrayList<Person>();
				int id;
				String name;
				int age;
				// ��cursor����һ��
				while(cursor.moveToNext())
				{
					id = cursor.getInt(0);
					name = cursor.getString(1);
					age = cursor.getInt(2);
					
					personList.add(new Person(id, name, age));
				}
				// �ر����ݿ�
				db.close();
				return personList;
			}
			
			// �ر����ݿ�
			db.close();
		}
		return null;
	}
	
	public Person queryItem(int id)
	{
		// ��ÿɶ������ݿ����
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// ������ݿ�򿪣�ִ�в�ѯ�Ĳ���
		if(db.isOpen())
		{
			// ִ�в�ѯ���ݿ�Ĳ���
			Cursor cursor = db.rawQuery("select _id, name, age from person where _id = ?;", new String[]{String.valueOf(id)});
			// ��cursor����Ϊ�գ��������ƶ�����һ����˵���в�ѯ�����
			if(null != cursor && cursor.moveToFirst())
			{
				int _id = cursor.getInt(0);
				String name = cursor.getString(1);
				int age = cursor.getInt(2);
				Person person = new Person(_id, name, age);
				return person;
			}
			// �ر����ݿ�
			db.close();
		}
		return null;
	}
}
