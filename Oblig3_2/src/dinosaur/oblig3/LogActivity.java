package dinosaur.oblig3;

import java.util.ArrayList;

import dinosaur.oblig3.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LogActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		int categoryIndex = getIntent().getExtras().getInt(MainActivity.CATEGORY_INDEX);
		String category = getResources().getStringArray(R.array.categories)[categoryIndex];
		
		setTitle("Logg - " + category);
		
		// HENT SHITN
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Sendt SMS,11.02.2011 20:33");
		temp.add("Ringt mordi,11.02.2011 21:37");
		getListView().setAdapter(new LogAdapter(this, R.layout.row_log, temp));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_log, menu);
		return true;
	}

	private class LogAdapter extends ArrayAdapter<String> {
		private ArrayList<String> entries;
		
		public LogAdapter(Context context, int textViewResourceId, ArrayList<String> entries) {
			super(context, textViewResourceId, entries);
			this.entries = entries;
		}
		
		@Override
        public View getView(final int position, View view, ViewGroup parent) {
            final View v = getLayoutInflater().inflate(R.layout.row_log, null);
            TextView dt = (TextView)v.findViewById(R.id.datetime);
            dt.setFocusable(false);
            dt.setFocusableInTouchMode(false);
            dt.setText(entries.get(position).split(",")[1]);
            
            TextView status = (TextView)v.findViewById(R.id.status);
            status.setFocusable(false);
            status.setFocusableInTouchMode(false);
            status.setText(entries.get(position).split(",")[0]);
            
            return v;
		}
	}
}
