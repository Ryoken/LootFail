package edu.mines.kschulz_chthomps.lootfail;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
	
/**
 * Provides content from an item database.
 * This code was modified from that obtainable from the following android tutorial:
 * 
 *   http://www.vogella.com/articles/AndroidSQLite/article.html
 *   http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 *   http://www.vogella.com/articles/AndroidSQLite/article.html#todo_contentprovider
 */
public class ItemProvider extends ContentProvider {

	// the database factory
	private ItemFactory _db;

	// used for the UriMacher
	private static final int ITEMS = 10;
	private static final int ITEM_ID = 20;
	private static final String AUTHORITY = "edu.mines.kschulz_chthomps.lootfail.ItemProvider";
	private static final String BASE_PATH = "items";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/items";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/item";
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sURIMatcher.addURI( AUTHORITY, BASE_PATH, ITEMS );
		sURIMatcher.addURI( AUTHORITY, BASE_PATH + "/#", ITEM_ID );
	}

	@Override
	public boolean onCreate() {
		_db = new ItemFactory(getContext());
		return false;
	}

	@Override
	public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

		// using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// check if the caller has requested a column which does not exist
		checkColumns(projection);

		// set the table
		queryBuilder.setTables(ItemFactory.TABLE);

		int uriType = sURIMatcher.match(uri);
		switch(uriType){
			case ITEMS :
				break;
			case ITEM_ID :

				// Adding the ID to the original query
				queryBuilder.appendWhere(ItemFactory.ID + "=" + uri.getLastPathSegment());
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = _db.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri );

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values){
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = _db.getWritableDatabase();

		long id = 0;
		switch(uriType) {
			case ITEMS :
				id = sqlDB.insert(ItemFactory.TABLE, null, values);
				break;
			default :
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = _db.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
			case ITEMS :
				rowsDeleted = sqlDB.delete(ItemFactory.TABLE, selection, selectionArgs);
				break;
			case ITEM_ID :
				String id = uri.getLastPathSegment();
				if(TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(ItemFactory.TABLE, ItemFactory.ID + "=" + id, null);
				} else {
					rowsDeleted = sqlDB.delete(ItemFactory.TABLE, ItemFactory.ID + "=" + id + " and " + selection, selectionArgs);
				}
				break;
			default :
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = _db.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
			case ITEMS :
				rowsUpdated = sqlDB.update( ItemFactory.TABLE, values, selection, selectionArgs );
				break;
			case ITEM_ID :
				String id = uri.getLastPathSegment();
				if(TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(ItemFactory.TABLE, values, ItemFactory.ID + "=" + id, null);
				} else {
					rowsUpdated = sqlDB.update(ItemFactory.TABLE, values, ItemFactory.ID + "=" + id + " and " + selection, selectionArgs);
				}
				break;
			default :
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = {ItemFactory.NAME, ItemFactory.COUNT, ItemFactory.FINISHED, ItemFactory.ID };
		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String> (Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String> (Arrays.asList(available));

			// Check if all columns which are requested are available
			if(!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}