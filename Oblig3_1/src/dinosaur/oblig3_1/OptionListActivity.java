package dinosaur.oblig3_1;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class OptionListActivity extends ListActivity {
	private int[] CATEGORIES = new int[] {
			R.array.category_telephony,
			R.array.category_network,
			R.array.category_div
	};
	
	private int categoryIndex;
	private boolean[] data = new boolean[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_list);
		
		categoryIndex = getIntent().getExtras().getInt(MainActivity.CATEGORY_INDEX);
		String category = getResources().getStringArray(R.array.categories)[categoryIndex];
		
		setTitle("Kategori - " + category);
		
		String[] options = getResources().getStringArray(CATEGORIES[categoryIndex]);
		getListView().setAdapter(new OptionAdapter(this, R.layout.row_option, options));
		
		loadSettings();
		
		Button buttonSave = (Button)findViewById(R.id.button_save);
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				pushToDB();
				finish();
			}			
		});
	}
	
	private boolean[] getData() {
		Cursor result = getContentResolver().query(DatabaseProvider.SETTINGS_URI, new String[]{ "setting" }, null, null, null);
		boolean settings[] = new boolean[15];
		for (int i = 0; i < 15; i++) {
			settings[i] = false;
		}
		if (result.getCount() > 0) {
			result.moveToFirst();
			do {
				settings[result.getInt(0)] = true;
			} while (result.moveToNext());
		}
		return settings;
	}
	
	private void loadSettings() {
		boolean[] tempData = getData();
		for (int i = categoryIndex * 5, j = 0; i < categoryIndex * 5 + 5; i++, j++) {
			data[j] = tempData[i];
		}
	}
	
	private void pushToDB() {
		boolean[] tempData = getData();
		getContentResolver().delete(DatabaseProvider.SETTINGS_URI, null, null);

		for (int i = categoryIndex * 5, j = 0; i < categoryIndex * 5 + 5; i++, j++) {
			tempData[i] = data[j];
		}
		
		for (int i = 0; i < 15; i++) {
			if (tempData[i]) {
				ContentValues values = new ContentValues();
				values.put("setting", i);
				getContentResolver().insert(DatabaseProvider.SETTINGS_URI, values);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_option_list, menu);
		return true;
	}

	private class OptionAdapter extends ArrayAdapter<String> {
		private String[] entries;
		
		public OptionAdapter(Context context, int textViewResourceId, String[] entries) {
			super(context, textViewResourceId, entries);
			this.entries = entries;
		}
		
		@Override
        public View getView(final int position, View view, ViewGroup parent) {
            final View v = getLayoutInflater().inflate(R.layout.row_option, null);
            TextView dt = (TextView)v.findViewById(R.id.status);
            dt.setFocusable(false);
            dt.setFocusableInTouchMode(false);
            dt.setText(entries[position]);
            
            final CheckBox cb = (CheckBox)v.findViewById(R.id.checkbox);
            cb.setChecked(data[position]);
            cb.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View arg0) {
    				data[position] = cb.isChecked();
    			}			
    		});
            
            return v;
		}
	}
}
