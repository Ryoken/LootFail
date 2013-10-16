/**
 * Allows the user to add a new Item to track.  The user must input a name and a drop rate (as a percentage).
 */
package edu.mines.kschulz_chthomps.lootfail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends Activity {

	private EditText formName;
	private EditText formChance;
	private Uri itemUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_layout);
	}

	/**
	 * Allows the user to cancel adding a new item if they change their mind
	 * @param v the button corresponding to this onClick method
	 */
	public void cancelAddItem(View v) {
		Intent i = new Intent(this, ItemListActivity.class);
		startActivity(i);
	}

	/**
	 * Allows the user to add a new item to track.
	 * Moves to the main activity when created.
	 * @param v button corresponding to this onClick method
	 */
	public void addItem(View v) {
		//Intent i = new Intent(this, MainActivity.class);
		formName = (EditText)findViewById( R.id.add_item_name_field );
		formChance = (EditText)findViewById( R.id.add_item_drop_rate_field );

		// gather form information
		boolean isName = !TextUtils.isEmpty(formName.getText().toString());
		boolean isChance = !TextUtils.isEmpty(formChance.getText().toString());
		Double chance = null;
		try {
			chance = Double.parseDouble(formChance.getText().toString());
		} catch(NumberFormatException e) {}

		// validate that fields are not empty and that the drop rate is numeric and less than 100
		if (isName && isChance && chance != null && chance <= 100) {

			// validation succeeded
			setResult(RESULT_OK);
			finish();
		} else {

			// validation failed
			Toast.makeText( AddItemActivity.this, "Please enter valid input (name and drop rate required, drop rate must be less than or equal to 100).", Toast.LENGTH_LONG ).show();
		}
	}

	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(ItemProvider.CONTENT_ITEM_TYPE, itemUri);
	}
	
	@Override
	protected void onPause()
	{
	  super.onPause();
	  saveState();
	}
	
	private void saveState()
	{
		String name = formName.getText().toString();
		String chance = formChance.getText().toString();
		if(name.length() == 0 && chance.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(ItemFactory.NAME, name);
		values.put(ItemFactory.CHANCE, chance);
		itemUri = getContentResolver().insert(ItemProvider.CONTENT_URI, values);
	}
}
