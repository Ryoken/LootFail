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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuccessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		this.initialize();
		
	}

	/**
	 * Sets up everything once the activity is created.
	 */
	public void initialize() {
		TextView message = (TextView) findViewById(R.id.success_msg);
		int numTries = getIntent().getIntExtra("temp tries", 0);
		float temp = (float) (1.00/numTries);
		message.setText("It took " + numTries + " tries to get the item, this gives a susscess rate of " + temp);
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