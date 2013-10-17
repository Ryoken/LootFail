package edu.mines.kschulz_chthomps.lootfail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Description: This activity displays an item's details.
 *              The main activity you are looking for is ItemListActivity.
 * 
 * @author: Kyle "Ryoken" Schulz
 * @author: Christina Thompson
 */
public class MainActivity extends Activity {

	private Uri uri;
	TextView count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// get the elements we need
		TextView name = (TextView) findViewById(R.id.percent_tracker_item_name_value),
		         chance = (TextView) findViewById(R.id.percent_tracker_drop_rate_value);
		count = (TextView) findViewById(R.id.percent_tracker_user_attempts_value);
		Bundle extras = getIntent().getExtras();
		uri = extras.getParcelable(ItemProvider.CONTENT_ITEM_TYPE);

		// populate the elements
		String[] projection = {ItemFactory.NAME, ItemFactory.CHANCE, ItemFactory.COUNT};
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			name.setText(cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.NAME)));
			chance.setText( cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.CHANCE)));
			count.setText( cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.COUNT)));
			cursor.close();
		}
	}


	/*
	 * Called when the success button is clicked.
	 * Sets the item to complete, then renders the success view.
	 */
	public void success(View v) {
		ContentValues values = new ContentValues();
		values.put(ItemFactory.FINISHED, 1);
		getContentResolver().update(uri, values, null, null);
		Intent i = new Intent(this, SuccessActivity.class);
		i.putExtra(ItemProvider.CONTENT_ITEM_TYPE, uri);
		startActivity(i);
	}

	/*
	 * Called when the failure button is clicked.
	 * Adds 1 to the count, then renders the list.
	 */
	public void failure(View v) {
		ContentValues values = new ContentValues();
		values.put(ItemFactory.COUNT, Integer.parseInt(count.getText().toString()) + 1);
		getContentResolver().update(uri, values, null, null);
		Intent i = new Intent(this, ItemListActivity.class);
		startActivity(i);
	}

	/*
	 * Called when the reset button is clicked.
	 * A Prompt dialogue will confirm intent.
	 * Sets the count to 0, then renders the list.
	 */
	public void reset(View v) {
		ContentValues values = new ContentValues();
		values.put(ItemFactory.COUNT, 0);
		getContentResolver().update(uri, values, null, null);
		final Intent i = new Intent(this, ItemListActivity.class);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.reset_item_message));
		builder.setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				startActivity(i);
			}
		});
		builder.show();
	}

	/*
	 * Called when the delete button is clicked.
	 * A Prompt dialogue will confirm intent.
	 * Removes the item, then renders the list.
	 */
	public void delete(View v) {
		getContentResolver().delete(uri, null, null);
		final Intent i = new Intent(this, ItemListActivity.class);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.delete_item_message));
		builder.setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				startActivity(i);
			}
		});
		builder.show();
	}
}
