package learn2crack.jsonparsing;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;

import learn2crack.jsonparsing.library.JSONParser;

import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {


	//JSON response tags
	private static final String TAG_ENTRY = "entries";
	private static final String TAG_ID = "id";
	private static final String TAG_TITLE = "title";
	private static final String TAG_USER = "user";

	class Entry{
		public String EntryID;
		public String UserName;
		public String Entry;
		public String id;
	}

	ArrayList<Entry> entryData = new ArrayList<Entry>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
		final int day = datePicker.getDayOfMonth();
		final int month = datePicker.getMonth()+1;
		final int year = datePicker.getYear();

		String URL = "http://sporlin.com/debe/json.php?date=" + day + "-" + month + "-" + year;

		Button getirButton = (Button) findViewById(R.id.button1);
		getirButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String URL = "http://sporlin.com/debe/json.php?date=" + day + "-" + month + "-" + year;
				Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();

			}
		});

		// Creating new JSON Parser
		JSONParser jParser = new JSONParser();

		// Getting JSON from URL
		JSONObject getJSON = jParser.getJSONFromUrl(URL);

		FancyAdapter fancyAdapter =null;
		JSONArray jsonArray = null;
		try {
			// Getting JSON Array
			jsonArray = getJSON.getJSONArray(TAG_ENTRY);
			for(int i=0; i< jsonArray.length();i++){
				JSONObject c = jsonArray.getJSONObject(i);
				Entry resultRow = new Entry();
				resultRow.Entry = c.getString(TAG_TITLE);
				resultRow.EntryID = c.getString(TAG_ID);
				resultRow.UserName = c.getString(TAG_USER);
				resultRow.id = c.getString(TAG_ID);
				entryData.add(resultRow);
			}

			ListView myListView = (ListView)findViewById(R.id.listView);
			myListView.setOnItemClickListener(this);


			fancyAdapter = new FancyAdapter();
			myListView.setAdapter(fancyAdapter);


		} catch (JSONException e) {
			e.printStackTrace();
		}



	}




	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
		String title = ((TextView) v.findViewById(R.id.name)).getText().toString();
		Intent intent = new Intent();
		intent.setClass(this, ItemDetail.class);
		intent.putExtra("position", position);
		intent.putExtra("id", id);
		intent.putExtra("title", title);
		startActivity(intent);
	}


	class FancyAdapter extends ArrayAdapter<Entry>{

		public FancyAdapter() {
			super(MainActivity.this, android.R.layout.simple_list_item_1,entryData);
		}

		public View getView(int position, View convertView,ViewGroup parent){
			ViewHolder holder;

			if(convertView==null){
				LayoutInflater inflater = getLayoutInflater();
				convertView=inflater.inflate(R.layout.row,null);

				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder=(ViewHolder)convertView.getTag();
			}
			holder.populateFrom(entryData.get(position));
			return(convertView);
		}
	}

	class ViewHolder{
		public TextView Entry = null;
		public TextView ID = null;
		public TextView UserName = null;

		ViewHolder(View row){
			Entry=(TextView)row.findViewById(R.id.name);
			ID=(TextView)row.findViewById(R.id.email);
			UserName=(TextView)row.findViewById(R.id.uname);
		}

		void populateFrom(Entry r){
			Entry.setText(r.Entry);
			ID.setText(r.id);
			UserName.setText(r.UserName);
		}
	}


}
