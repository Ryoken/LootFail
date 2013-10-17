/**
 * Description: This activity will display when an event is marked as successful.
 *              It will display the total number of failures n and the percent chance of that
 *              bad luck streak of happening.  Note that this is not the chance for a successful
 *              event, but rather the chance of a successful event after n number of failures.
 *              As listed in the manifest:
 *                  android:minSdkVersion="16"
 *                  android:targetSdkVersion="17"
 * 
 * Documentation Statement: The code contained was created solely by the author listed below.
 *                          Some code was referenced from in-class demos or internet searches,
 *                          but none was used directly.
 * 
 * @author: Kyle "Ryoken" Schulz
 */

package edu.mines.kschulz_chthomps.lootfail;
import java.math.BigDecimal;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SuccessActivity extends Activity {

	private Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_success);
		
		Bundle extras = getIntent().getExtras();
		uri = extras.getParcelable(ItemProvider.CONTENT_ITEM_TYPE);
		this.initialize();
	}

	/**
	 * Sets up everything once the activity is created.
	 */
	public void initialize() {
		TextView message = (TextView) findViewById(R.id.success_msg);
		String[] projection = {ItemFactory.NAME, ItemFactory.CHANCE, ItemFactory.COUNT};
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			int numTries = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.COUNT)));
			String name = cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.NAME));
			BigDecimal chance = getStreakChance(numTries, Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(ItemFactory.CHANCE))));
			String messageText = String.format(Locale.getDefault(),"It took %d tries to get %s\n There was a %0.2f% chance of that happening\n" +
					"you are %s Unlucky!", numTries, name, chance, getUnlucky(chance));
			message.setText(messageText);
			cursor.close();
		}
	}

	/**
	 * returns the chance of the current bad luck streak happening
	 * 
	 * @return
	 */
	private BigDecimal getStreakChance(int tries, double odds) {
		BigDecimal chance = new BigDecimal((Math.pow(1 - (odds / 100), tries)) * 100);
		return chance.setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * returns a description of the relative unluckiness
	 * 
	 * @return
	 */
	private String getUnlucky(BigDecimal chance) {
		double chanceDouble = chance.doubleValue();
		if (chanceDouble > 50) {
			return "not at all";
		} else if (chanceDouble > 25) {
			return "not very";
		} else if (chanceDouble > 10) {
			return "pretty";
		} else if (chanceDouble > 5) {
			return "very";
		} else if (chanceDouble > 1) {
			return "extremely";
		} else if (chanceDouble > .1) {
			return "unbelievably";
		} else {
			return "incredibly";
		}
	}

	/**
	 * Returns the user to the main screen
	 * @param v
	 */
	public void returnToItemList(View v)
	{
		Intent i = new Intent(this, ItemListActivity.class);
		startActivity(i);
	}

	/**
	 * Returns the user to the item tracking screen
	 * @param v
	 */
	public void returnToItemTracking(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("Item Name", "temp");
		startActivity(i);
	}
}