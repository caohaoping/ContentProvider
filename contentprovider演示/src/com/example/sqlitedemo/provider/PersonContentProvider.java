package com.example.sqlitedemo.provider;

import com.example.sqlitedemo.db.PersonSQLiteOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonContentProvider extends ContentProvider
{
	// content://com.example.provider/person/10

	private static final String AUTHORITY = "com.example.sqlitedemo.provider.PersonContentProvider";
	private static final int PERSON_INSERT_CODE = 0;
	private static final int PERSON_DELETE_CODE = 1;
	private static final int PERSON_UPDATE_CODE = 2;
	private static final int PERSON_QUERYALL_CODE = 3;
	private static final int PERSON_QUERYITEM_CODE = 4;

	private static UriMatcher uriMatcher;

	private PersonSQLiteOpenHelper mOpenHelper; // person表的数据库帮助对象

	static
	{
		// 常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// 添加一些uri（分机号）
		// content://com.example.sqlitedemo.provider.PersonContentProvider/person/insert
		uriMatcher.addURI(AUTHORITY, "person/insert", PERSON_INSERT_CODE);
		// content://com.example.sqlitedemo.provider.PersonContentProvider/person/delete
		uriMatcher.addURI(AUTHORITY, "person/delete", PERSON_DELETE_CODE);
		// content://com.example.sqlitedemo.provider.PersonContentProvider/person/delete
		uriMatcher.addURI(AUTHORITY, "person/update", PERSON_UPDATE_CODE);
		// content://com.example.sqlitedemo.provider.PersonContentProvider/person/queryAll
		uriMatcher.addURI(AUTHORITY, "person/queryAll", PERSON_QUERYALL_CODE);
		// content://com.example.sqlitedemo.provider.PersonContentProvider/person/query/230
		uriMatcher.addURI(AUTHORITY, "person/query/#", PERSON_QUERYITEM_CODE);

	}

	@Override
	public boolean onCreate()
	{
		mOpenHelper = new PersonSQLiteOpenHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		switch (uriMatcher.match(uri))
		{
		case PERSON_QUERYALL_CODE:
			SQLiteDatabase db = mOpenHelper.getReadableDatabase();
			if (db.isOpen())
			{
				Cursor cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
				return cursor;
			}
			break;
		case PERSON_QUERYITEM_CODE:
			SQLiteDatabase db1 = mOpenHelper.getReadableDatabase();
			if (db1.isOpen())
			{
				/**
				 * content://com.example.sqlitedemo.provider.PersonContentProvider/person/query/230
				 * ContentUris 类用于获取Uri路径后面的ID部分 1)为路径加上ID: withAppendedId(uri,id) 2)
				 * 从路径中获取ID: parseId(uri)
				 */
				long id = ContentUris.parseId(uri);
				Cursor cursor = db1.query("person", projection, "_id = ?", new String[] { String.valueOf(id) }, null,
						null, sortOrder);
				return cursor;
			}

		default:
			throw new IllegalArgumentException("uri不匹配" + uri);
		}

		return null;
	}

	@Override
	public String getType(Uri uri)
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		switch (uriMatcher.match(uri))
		{
		case PERSON_INSERT_CODE: // 添加到person表中
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			if (db.isOpen())
			{
				long id = db.insert("person", null, values);
				Uri withAppendedId = ContentUris.withAppendedId(uri, id);

				// 通知内容观察者改变
				getContext().getContentResolver().notifyChange(
						Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider"), null);

				db.close();

				return withAppendedId;
			}
			break;

		default:
			throw new IllegalArgumentException("uri不匹配" + uri);
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		switch (uriMatcher.match(uri))
		{
		case PERSON_DELETE_CODE:
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			if (db.isOpen())
			{
				int count = db.delete("person", selection, selectionArgs);

				// 通知内容观察者改变
				getContext().getContentResolver().notifyChange(
						Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider"), null);

				db.close();
				return count;
			}
			break;

		default:
			throw new IllegalArgumentException("uri不匹配" + uri);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		switch (uriMatcher.match(uri))
		{
		case PERSON_UPDATE_CODE:
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			if (db.isOpen())
			{
				int count = db.update("person", values, selection, selectionArgs);

				// 通知内容观察者改变
				getContext().getContentResolver().notifyChange(
						Uri.parse("content://com.example.sqlitedemo.provider.PersonContentProvider"), null);

				db.close();
				return count;
			}
			break;

		default:
			throw new IllegalArgumentException("uri不匹配" + uri);
		}
		return 0;
	}

}
