package com.bashar.salatreminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

	// TABLE INFORMATTION
	public static final String TABLE_MEMBER = "member";
	public static final String MEMBER_ID = "_id";
	public static final String MEMBER_NAME = "name2";
	public static final String MEMBER_NUMBER = "number";
	public static final String MEMBER_FAJR = "fajr";
	public static final String MEMBER_DHUR = "dhur";
	public static final String MEMBER_ASR = "asr";
	public static final String MEMBER_MAG = "mag";
	public static final String MEMBER_ESHA = "esha";
		
		public static final String TABLE_MESSAGE = "message";
		public static final String MESSAGE_TEXT = "messageText";
		public static final String MESSAGE_DATE = "messageDate";
		

		// DATABASE INFORMATION
		static final String DB_NAME = "MEMBER.DB";
		static final int DB_VERSION = 1;
		
	// TABLE CREATION STATEMENT
	private static final String CREATE_MESSAGE_LOG_TABLE = "create table "
			+ TABLE_MESSAGE + "(" + MEMBER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MESSAGE_TEXT + " TEXT, "
			+ MESSAGE_DATE + " TEXT NOT NULL, "
			+ MEMBER_NAME + " TEXT, "
			+ MEMBER_NUMBER + " TEXT);";
	
	
	private static final String CREATE_TABLE = "create table "
			+ TABLE_MEMBER + "(" + MEMBER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEMBER_NAME + " TEXT NOT NULL, "
			+ MEMBER_NUMBER + " TEXT NOT NULL, "
			+ MEMBER_FAJR + " TEXT NOT NULL, "
			+ MEMBER_DHUR + " TEXT NOT NULL, "
			+ MEMBER_ASR + " TEXT NOT NULL, "
			+ MEMBER_MAG + " TEXT NOT NULL, "
			+ MEMBER_ESHA + " TEXT);";

	public DBhelper(Context context) {
		super(context, DB_NAME, null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MESSAGE_LOG_TABLE);
		db.execSQL(CREATE_TABLE);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
		onCreate(db);
	}
}