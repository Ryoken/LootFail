/**
 * A factory for interfacing with the items database.
 * This code was modified from that obtainable from the following android tutorial:
 * 
 *   http://www.vogella.com/articles/AndroidSQLite/article.html
 *   http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 *   http://www.vogella.com/articles/AndroidSQLite/article.html#todo_contentprovider
 */
package edu.mines.kschulz_chthomps.lootfail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemFactory extends SQLiteOpenHelper {

	// database information
	public static final String DB = "items.db";
	private static final int VERSION = 3;

	// database fields
	public static final String TABLE = "item",
	                           ID = "_id",
	                           NAME = "item",
	                           CHANCE = "chance",
	                           COUNT = "count",
	                           FINISHED = "finished";

	// database creation
	private static final String CREATE = 
			"create table " + TABLE + "(" +
			ID + " integer primary key autoincrement, " +
			NAME + " text not null, " +
			CHANCE + " real not null, " +
			COUNT + " integer default 0, " +
			FINISHED + " integer default 0);";

	// database removal
	private static final String DELETE =
			"DROP TABLE IF EXISTS " + TABLE;

	public ItemFactory (Context context) {
		super(context, DB, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(DELETE);
		onCreate(db);
	}
}