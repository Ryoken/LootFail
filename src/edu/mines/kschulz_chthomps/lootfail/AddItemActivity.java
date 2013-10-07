/**
 * 
 * Allows the user to add a new Item to track.  The user must input a name for the item. 
 * Possibly they must add a drop rate for the item(provided by wowhead.com or some other source)
 */
package edu.mines.kschulz_chthomps.lootfail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddItemActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_layout);
	}
	
	/**
	 * Allows the user to cancel adding a new item if they change their mind
	 * @param v the button corresponding to this onClick method
	 */
	public void cancelAddItem(View v)
	{
		Intent i = new Intent(this, ItemListActivity.class);
		startActivity(i);
		
	}
	
	/**
	 * Allows the user to add a new item to track
	 * Currently moves to the main activity
	 * Will update the database to reflect the added item
	 * Moves to the main activity
	 * @param v button corresponding to this onClick method
	 */
	public void addItem(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("Item Name", "TEMP");
		startActivity(i);
	}
}
