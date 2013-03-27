package dinosaur.oblig3;

import java.util.ArrayList;

import dinosaur.oblig3.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends ListActivity {
	private String currentCategory;
	
	private class DatabaseObserver extends ContentObserver {
		public DatabaseObserver(Handler handler) {
			super(handler);
		}
		
		public void onChange(boolean selfChange) {
			getLog(currentCategory);
			getListView().setSelection(getListView().getAdapter().getCount() - 1);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		int categoryIndex = getIntent().getExtras().getInt(MainActivity.CATEGORY_INDEX);
		currentCategory = getResources().getStringArray(R.array.categories)[categoryIndex];
		
		setTitle("Logg - " + currentCategory);
		
		getLog(currentCategory);
	    
		getContentResolver().registerContentObserver(Uri.parse("content://dinosaur.oblig3_1.DatabaseProvider/log"), false, new DatabaseObserver(new Handler()));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_log, menu);
		return true;
	}
	
	private void getLog(String category) {
		String[] projection = { "_id", "content", "details", "datetime" };
		Cursor result = getContentResolver().query(Uri.parse("content://dinosaur.oblig3_1.DatabaseProvider/log/category/" + category), projection, null, null, null);

		ArrayList<Entry> entries = new ArrayList<Entry>();
		if (result.getCount() > 0) {
			result.moveToFirst();
			do {
				entries.add(new Entry(result.getString(1), result.getString(2), result.getString(3)));
			} while (result.moveToNext());
		}
		
		getListView().setAdapter(new LogAdapter(this, R.layout.row_log, entries));
	}
	
	private class Entry {
		private String content;
		private String details;
		private String datetime;
		
		public Entry(String content, String details, String datetime) {
			this.content = content;
			this.details = details;
			this.datetime = datetime;
		}
		
		public String getContent() { return content; }
		public String getDetails() { return details; }
		public String getDatetime() { return datetime; }
	}

	private class LogAdapter extends ArrayAdapter<Entry> {
		private ArrayList<Entry> entries;
		
		public LogAdapter(Context context, int textViewResourceId, ArrayList<Entry> entries) {
			super(context, textViewResourceId, entries);
			this.entries = entries;
		}
		
		@Override
        public View getView(final int position, View view, ViewGroup parent) {
            final View v = getLayoutInflater().inflate(R.layout.row_log, null);
            TextView dt = (TextView)v.findViewById(R.id.datetime);
            dt.setFocusable(false);
            dt.setFocusableInTouchMode(false);
            dt.setText(entries.get(position).getDatetime());
            
            TextView status = (TextView)v.findViewById(R.id.status);
            status.setFocusable(false);
            status.setFocusableInTouchMode(false);
            status.setText(entries.get(position).getContent());
            
            TextView details = (TextView)v.findViewById(R.id.details);
            details.setFocusable(false);
            details.setFocusableInTouchMode(false);
            details.setText(entries.get(position).getDetails());
            
            return v;
		}
	}
}
