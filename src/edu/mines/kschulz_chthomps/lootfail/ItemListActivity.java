package edu.mines.kschulz_chthomps.lootfail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
public class ItemListActivity extends Activity {
	private Context context;	//Used in creating the intent for moving to the main activity when a list item is clicked
	private ItemAdapter adapter;	//Adapter used for the listview
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loot_picker_list);
		context = getApplicationContext();
		ListView itemList = (ListView) findViewById(R.id.saved_items);
		adapter = new ItemAdapter(this);
		itemList.setAdapter(adapter);
		itemList.setOnItemClickListener(new OnItemClick());
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
}