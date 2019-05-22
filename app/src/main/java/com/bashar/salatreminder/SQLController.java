package com.bashar.salatreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class SQLController {

	private DBhelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new DBhelper(ourcontext);
		database = dbhelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	public void insertData(String name2, String phone, String fajr, String dhur,
                           String asr, String mag, String esha) {
		ContentValues cv = new ContentValues();
		cv.put(DBhelper.MEMBER_NAME, name2);
		cv.put(DBhelper.MEMBER_NUMBER, phone);
		cv.put(DBhelper.MEMBER_FAJR, fajr);
		cv.put(DBhelper.MEMBER_DHUR, dhur);
		cv.put(DBhelper.MEMBER_ASR, asr);
		cv.put(DBhelper.MEMBER_MAG, mag);
		cv.put(DBhelper.MEMBER_ESHA, esha);
		database.insert(DBhelper.TABLE_MEMBER, null, cv);
	}

	public void insertMessageData(String messageText, String messageDate,
                                  String name2, String phone) {

		ContentValues cv = new ContentValues();
		cv.put(DBhelper.MESSAGE_TEXT, messageText);
		cv.put(DBhelper.MESSAGE_DATE, messageDate);
		cv.put(DBhelper.MEMBER_NAME, name2);
		cv.put(DBhelper.MEMBER_NUMBER, phone);
		try {
			database.insert(DBhelper.TABLE_MESSAGE, null, cv);
			//Toast.makeText(ourcontext, "messageinsert", 2000).show();
		} catch (Exception e) {
			//Toast.makeText(ourcontext, "message not insert", 2000).show();
		}

	}

	public Cursor readData() {
		String[] allColumns = new String[] { DBhelper.MEMBER_ID,
				DBhelper.MEMBER_NAME, DBhelper.MEMBER_NUMBER, DBhelper.MEMBER_FAJR,
                DBhelper.MEMBER_DHUR, DBhelper.MEMBER_ASR, DBhelper.MEMBER_MAG, DBhelper.MEMBER_ESHA};
		Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor readMessageData() {
		// String table = "select * from ";
		// table = table + DBhelper.TABLE_MESSAGE;
		// try{
		// Cursor c = database.rawQuery(table, null);
		// int count= c.getCount();
		// return c;
		// }
		// catch(Exception E)
		// {
		// //Toast.makeText(this, "message insert", 2000).show();
		// }

		try {
			String[] allColumns = new String[] { DBhelper.MEMBER_ID,
					DBhelper.MESSAGE_TEXT, DBhelper.MESSAGE_DATE,
					DBhelper.MEMBER_NAME, DBhelper.MEMBER_NUMBER };
			Cursor c = database.query(DBhelper.TABLE_MESSAGE, allColumns, null,
					null, null, null, null);
			int count = c.getCount();
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		} catch (Exception e) {
			Log.e("NO value", "0");
		}
		return null;
	}

	public int updateData(long memberID, String name2, String memberNumber, String fajr, String dhur,
                          String asr, String mag, String esha) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(DBhelper.MEMBER_NAME, name2);
		cvUpdate.put(DBhelper.MEMBER_NUMBER, memberNumber);
        cvUpdate.put(DBhelper.MEMBER_FAJR, fajr);
        cvUpdate.put(DBhelper.MEMBER_DHUR, dhur);
        cvUpdate.put(DBhelper.MEMBER_ASR, asr);
        cvUpdate.put(DBhelper.MEMBER_MAG, mag);
        cvUpdate.put(DBhelper.MEMBER_ESHA, esha);
		//Toast.makeText(ourcontext, esha, Toast.LENGTH_LONG).show();
		int i = database.update(DBhelper.TABLE_MEMBER, cvUpdate,
				DBhelper.MEMBER_ID + " = " + memberID, null);
		return i;
	}

	public void deleteData(long id) {
		database.delete(DBhelper.TABLE_MEMBER, DBhelper.MEMBER_ID + "="
				+ id, null);
	}

	public void deleteMessageData(long memberID) {
		
		database.delete(DBhelper.TABLE_MESSAGE, DBhelper.MEMBER_ID + "="
				+ memberID, null);
		
		
	}

	public int getcount(String tableName) {
		String table = "select * from ";
		table = table + tableName;
		try {
			Cursor c = database.rawQuery(table, null);
			return c.getCount();
		} catch (Exception E) {
			// Toast.makeText(this, "message insert", 2000).show();
		}
		return 0;
	}

	public Cursor readSpecificData(String tableName, long row_id) {
		// try {
		// String
		// query="select * from "+DBhelper.TABLE_MESSAGE+" where "+DBhelper.MEMBER_ID+" = "
		// +row_id;
		// Cursor c = database.rawQuery(query, null);
		// return c;
		// } catch (Exception e) {
		// Log.e("Durjay","Not Found");
		//
		// }
		String[] allColumns = new String[] { DBhelper.MEMBER_ID,
				DBhelper.MESSAGE_TEXT, DBhelper.MESSAGE_DATE,
				DBhelper.MEMBER_NAME, DBhelper.MEMBER_NUMBER };
		try {
			Cursor c = database.query(tableName, allColumns, DBhelper.MEMBER_ID
					+ "=" + row_id, null, null, null, null);
			int count = c.getCount();
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		} catch (Exception e) {
		}

		return null;
	}
	public int countSpecificStringData(String tableName, String phn_no) {
		String[] allColumns = new String[] { DBhelper.MEMBER_ID,
				DBhelper.MEMBER_NAME, DBhelper.MEMBER_NUMBER };
		try{
		Cursor c = database.query(tableName, allColumns, DBhelper.MEMBER_NUMBER+"="+ "'"+phn_no+"'",
				null, null, null, null);
		return c.getCount();
		}
		catch(Exception e){
		}
		
		return 0;
	}
	
}// outer class end
