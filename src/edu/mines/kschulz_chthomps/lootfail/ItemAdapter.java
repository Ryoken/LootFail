/**
 * 
 * Custom adapter for the ListView in ItemListActivity
 * 
 * The data is populated from the database
 * 
 * I didnt really use resources since Ive dont a bunch of these for work but we can say I used this if you want
 * http://www.vogella.com/articles/AndroidListView/article.html
 */
package edu.mines.kschulz_chthomps.lootfail;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Integer> primaryKeys = new ArrayList<Integer>();	//Will be used when the API for the database is set up to get data 
	//Temporary data for populating the listview
	private String[] itemNames = {"Benediction", "Golad, Twilight of Aspects", "Tiriosh, Nightmare of Ages", "Dragonwrath, Tarecgosa's Rest", "Shadowmourne"};

	public ItemAdapter(Context context)
	{
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return itemNames.length;
	}

	/**
	 * Returns the name of a loot item 
	 * 
	 * @param position position of the ListView click
	 */
	@Override
	public String getItem(int position) {
		return itemNames[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Creates the view displayed in the ListView at a position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = inflater.inflate(R.layout.loot_list_item, null);
		TextView name = (TextView) v.findViewById(R.id.list_item_name_value);
		name.setText(itemNames[position]);
		
		TextView dropRate = (TextView) v.findViewById(R.id.list_item_drop_rate_value);
		Random random = new Random();
		
		dropRate.setText(random.nextInt(100) + "%");
		
		TextView numTries = (TextView) v.findViewById(R.id.list_item_num_tries_value);
		numTries.setText(String.valueOf(random.nextInt(10)));
		return v;
	}
}
