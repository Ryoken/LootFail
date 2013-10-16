package edu.mines.kschulz_chthomps.lootfail;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Description: This activity will display a number of events with a set percent chance of success.
 *              The user may keep track of a series of failed events before a success.
 *              As listed in the manifest:
 *                  android:minSdkVersion="17"
 *                  android:targetSdkVersion="18"
 * 
 * Documentation Statement: The code contained was created solely by the author listed below.
 *                          Some code was referenced from in-class demos or Internet searches,
 *                          but none was used directly.
 * 
 * @author: Kyle "Ryoken" Schulz
 * @author: Christina Thompson
 */
public class ItemListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	// Used in creating the intent for moving to the main activity when a list item is clicked
	private Context context;

	// Adapter used for the listview
	//private ItemAdapter listAdapter;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loot_picker_list);
		context = getApplicationContext();
		
		// clicking on an item causes the main activity to launch
		ListView itemList = (ListView) findViewById(android.R.id.list);
		itemList.setOnItemClickListener(new OnItemClick());

		this.getListView();
		//listAdapter = new ItemAdapter(this);
		//itemList.setAdapter(listAdapter);
		populate();
		
	}
	/*
	 * Populates the list with items from the database
	 */
	private void populate() {
		String[] from = new String[] {ItemFactory.NAME, ItemFactory.CHANCE, ItemFactory.COUNT};
		int[] to = new int[] {R.id.list_item_name_value, R.id.list_item_drop_rate_value, R.id.list_item_num_tries_value};
		getLoaderManager().initLoader(0, null, this);
		this.adapter = new SimpleCursorAdapter(this, R.layout.loot_list_item, null, from, to, 0);
		setListAdapter(this.adapter);
	}

	/**
	 * This may eventually be used to set the theme for the app
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loot_picker_list, menu);
		return true;
	}


	/*
	 * UI functionality 
	 */

	/**
	 * Allows the user to move to the activity that will let them add a new item to track
	 * @param v the button corresponding to this onClick method
	 */
	public void addNewItem(View v)
	{
		Intent i = new Intent(this,AddItemActivity.class);
		startActivity(i);
	}

	/**
	 * OnClick listener for our list
	 * When an item in the list is clicked, the Main Activity is started 
	 * Currently uses intent extras to set the name of the item corresponding to the list click
	 * Will eventually pass the primary key to the Main activity to populate data
	 * @author cthompson
	 *
	 */
	private class OnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent i = new Intent(context, MainActivity.class);
			i.putExtra("Item Name", adapter.getItem(arg2).toString());
			startActivity(i);
		}
	}

	/**
	 * Creates a new loader after the initLoader() call
	 */
	@Override
	public Loader<Cursor> onCreateLoader (int id, Bundle args){
		String[] projection = {ItemFactory.ID, ItemFactory.NAME, ItemFactory.CHANCE, ItemFactory.COUNT};
		CursorLoader cursorLoader = new CursorLoader(this, ItemProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	/** 
	 * Callback method from the loader.
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		this.adapter.swapCursor(data);
	}

	/**
	 * Callback method from the loader.
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		this.adapter.swapCursor(null);
	}
}