package edu.mines.kschulz_chthomps.lootfail;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Description: This activity is a placeholder.
 *              The main activity you are looking for is ItemListActivity.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
