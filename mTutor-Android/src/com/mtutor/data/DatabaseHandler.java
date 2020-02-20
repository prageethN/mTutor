package com.mtutor.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ADVERTISEME_DB";

	// Favorite Seller table name
	private static final String TABLE_WATCH_LIST = "MTUTOR_WATCH_LIST";	
	private static final String TABLE_SAVED_SEARCH = "MTUTOR_SAVED_SEARCH";

	// Favorite Seller Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USER_ID = "userid";
	private static final String KEY_ATT_ID = "attachmentid";
	private static final String KEY_DOC_ID = "documentid";
	private static final String KEY_TYPE_ID = "typeid";
	private static final String KEY_SEARCH_QUERY = "searchQuery";
	private static final String KEY_CONDITION_FLG = "conditionFlg";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

			String CREATE_WATCH_LIST_TABLE = "CREATE TABLE " + TABLE_WATCH_LIST
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID
				+ " TEXT," + KEY_ATT_ID + " TEXT," + KEY_DOC_ID + " TEXT," + KEY_TYPE_ID + " TEXT"+")";
		db.execSQL(CREATE_WATCH_LIST_TABLE);
		
				
		String CREATE_SAVED_SEARCH_TABLE = "CREATE TABLE " + TABLE_SAVED_SEARCH
		+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID
		+ " TEXT," + KEY_SEARCH_QUERY + " TEXT," + KEY_CONDITION_FLG + " TEXT"+ ")";
		db.execSQL(CREATE_SAVED_SEARCH_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	

	/* -------------------------- Watch List --------------------------------- */

	public void addWatchListItem(String userID, String attID,String docID,String typeID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_ATT_ID, attID); // attachment ID
		values.put(KEY_DOC_ID, docID); // document ID
		values.put(KEY_TYPE_ID, typeID); // document ID
				
		// Inserting Row
		db.insert(TABLE_WATCH_LIST, null, values);
		db.close(); // Closing database connection
	}

	public ArrayList<ArrayList<String>> getWatchList(String userID,String typeID) {

		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_WATCH_LIST, new String[] { KEY_ID, KEY_USER_ID,
						KEY_ATT_ID,KEY_DOC_ID,KEY_TYPE_ID }, KEY_USER_ID + "=?"+ "AND "
						+ KEY_TYPE_ID + " = ?",
						new String[] { String.valueOf(userID), String.valueOf(typeID) }, null, null,
						null, null);
		if (cursor.moveToFirst())

			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

		return resultList;
	}

	public void deleteFromWatchList(String userID, String attID,String docID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WATCH_LIST, KEY_USER_ID + " = ?" + "AND "
				+ KEY_ATT_ID + " = ?"+ "AND "
				+ KEY_DOC_ID + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(attID), String.valueOf(docID) });
		db.close();
	}

	public int getWishListCount(String userID) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_WATCH_LIST, new String[] { KEY_ID, KEY_USER_ID,
						KEY_ATT_ID,KEY_DOC_ID,KEY_TYPE_ID }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		return cursor.getCount();
	}
	
	
	
	/* -------------------------- Saved Search --------------------------------- */

	public void addSavedSearchItem(String userID, String qString) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_SEARCH_QUERY, qString); // Seller ID
		values.put(KEY_CONDITION_FLG, "1");
		
		// Inserting Row
		db.insert(TABLE_SAVED_SEARCH, null, values);
		db.close(); // Closing database connection
	}
	
	public ArrayList<ArrayList> getSavedSearch(String userID) {

		ArrayList<ArrayList> resultList = new ArrayList<ArrayList>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(true,TABLE_SAVED_SEARCH, new String[] { KEY_USER_ID,
						KEY_SEARCH_QUERY,KEY_CONDITION_FLG }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);
		if (cursor.moveToFirst())

			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

		return resultList;
	}
	public void deleteFromSavedSearchList(String userID, String qString) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SAVED_SEARCH, KEY_USER_ID + " = ?" + "AND "
				+ KEY_SEARCH_QUERY + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(qString) });
		db.close();
	}
}