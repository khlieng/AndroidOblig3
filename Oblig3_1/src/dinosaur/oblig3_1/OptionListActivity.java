package dinosaur.oblig3_1;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;

public class OptionListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_option_list, menu);
		return true;
	}

}
