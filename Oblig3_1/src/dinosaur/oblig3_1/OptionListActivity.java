package dinosaur.oblig3_1;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class OptionListActivity extends ListActivity {
	private int[] CATEGORIES = new int[] {
			R.array.category_telephony,
			R.array.category_network,
			R.array.category_div
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_list);
		
		int categoryIndex = getIntent().getExtras().getInt(MainActivity.CATEGORY_INDEX);
		String category = getResources().getStringArray(R.array.categories)[categoryIndex];
		
		setTitle("Kategori - " + category);
		
		String[] options = getResources().getStringArray(CATEGORIES[categoryIndex]);
		getListView().setAdapter(new OptionAdapter(this, R.layout.row_option, options));
		
		Button buttonSave = (Button)findViewById(R.id.button_save);
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Skyv innstillinger til DB her
				finish();
			}			
		});
	}

	private void foo(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
            cb.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View arg0) {
    				foo(entries[position] + " " + cb.isChecked());
    			}			
    		});
            
            return v;
		}
	}
}
