package dinosaur.oblig3_1;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	public static final String CATEGORY_INDEX = "dinosaur.oblig3_1.MainActivity.CATEGORY_INDEX";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
