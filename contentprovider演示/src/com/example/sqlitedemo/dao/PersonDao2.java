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
 * �洢ҵ�񷽷���
 * ��ɾ�Ĳ����ݿ�
 * @author haopi
 */
public class PersonDao2
{
	// ���ݿ�İ��������
	private PersonSQLiteOpenHelper mOpenHelper;

	public PersonDao2(Context context)
	{
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
			ContentValues values = new ContentValues();
			values.put("name", person.getName());
			values.put("age", person.getAge());
//			long rowId = db.insert("person", "name", null); //Person [id=11, name=null, age=0]
			long rowId = db.insert("person", "name", values);
			Log.i("insert-rowId", "�����˵�" + rowId + "��");
			// �ر����ݿ�
			db.close();
		}
	}
	
	public void delete(int id)
	{
		// ��ÿ�д�����ݿ����
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		// ������ݿ�򿪣�ִ��ɾ���Ĳ���
		if(db.isOpen())
		{
			// ɾ������
			String whereClause = "_id = ?"; 
			//ɾ����������
			String[] whereArgs = {String.valueOf(id)};
			int number = db.delete("person", whereClause, whereArgs);
			Log.i("delete-number", "ɾ����" + number + "��");
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
			// ʵ��������ֵ
			ContentValues values = new ContentValues();
			// ��values���������
			values.put("name", name);
			// �޸�����
			String whereClause = "_id = ?";
			// �޸���������
			String[] whereArgs = {String.valueOf(id)};
			int number = db.update("person", values, whereClause, whereArgs);
			Log.i("update-number", "������" + number + "��");
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
			/**
			 * ��table:������
			 * ��columns:����������
			 * ��selection:�����־䣬�൱��where
			 * ��selectionArgs:�����־䣬��������
			 * ��groupBy:������
			 * ��having:��������
			 * ��orderBy:������
			 * ��limit:��ҳ��ѯ����
			 * ��Cursor:����ֵ���൱�ڽ����ResultSet
			 */
			//��ѯ����α�
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
			// �ر����ݿ�
			db.close();
			return personList;
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
			/**
			 * ��table:������
			 * ��columns:����������
			 * ��selection:�����־䣬�൱��where
			 * ��selectionArgs:�����־䣬��������
			 * ��groupBy:�����У��������
			 * ��having:�����������������
			 * ��orderBy:�����У�����
			 * ��limit:��ҳ��ѯ����
			 * ��Cursor:����ֵ���൱�ڽ����ResultSet
			 */
			// ��ѯ����α�
			Cursor cursor = db.query("person", new String[]{"_id", "name", "age"}, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
			
			while(cursor.moveToNext())
			{
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int age = cursor.getInt(cursor.getColumnIndex("age"));
				
				// �ر����ݿ�
				db.close();
				return new Person(_id, name, age);
			}
			// �ر����ݿ�
			db.close();
		}
		return null;
	}
}
