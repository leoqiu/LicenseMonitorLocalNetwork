package com.leo.LicenseMonitorLocalNetwork.db;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LDBHelper extends SQLiteOpenHelper{


	/**
	 * #1.
	 *  SQLiteOpenHelper usages
	 * 
	 *  getReadableDatabase()
	 *  getWritableDatabase()
	 *  onCreate(SQLiteDatabase db)
	 *  onOpen(SQLiteDatabase db)
	 *  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	 *  close()
	 * 
	 * #2.
	 *  sqlite3 test_leo_db : entering database
	 *  .schema
	 * 
	 */
	
	private static final int VERSION = 1;
	
	
	public LDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public LDBHelper(Context context, String name, int version){
		this(context, name, null, version);
	}
	
	public LDBHelper(Context context, String name){
		this(context, name, VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		println("db created...");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		println("upgrade db...");
	}

	private void println(String str){
		System.out.println("db operation ---> " + str);
	}


}
