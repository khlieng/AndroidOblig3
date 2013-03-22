package dinosaur.oblig3_1;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	public static final String CATEGORY_INDEX = "dinosaur.oblig3_1.MainActivity.CATEGORY_INDEX";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// DATABASETEST
		//
		
		ContentValues values = new ContentValues();
		values.put("content", "jadda");
		values.put("datetime", "YYYY-MM-DD HH:MM:SS");
		values.put("category", "Telefoni");
		getContentResolver().insert(DatabaseProvider.CONTENT_URI, values);
		
		String[] projection = { "_id", "content" };
		Cursor result = getContentResolver().query(DatabaseProvider.CONTENT_URI, projection, null, null, null);
		if (result == null) {
			Toast.makeText(this, "result = null :(", Toast.LENGTH_LONG).show();			
		}
		else {
			result.moveToFirst();
			Toast.makeText(this, ""+result.getString(1), Toast.LENGTH_LONG).show();
		}
		//
		//
		
		this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.categories)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onListItemClick(ListView listView, View view, int pos, long id) {
		Intent intent = new Intent(this, OptionListActivity.class);
		intent.putExtra(CATEGORY_INDEX, pos);
		startActivity(intent);
	}
}
