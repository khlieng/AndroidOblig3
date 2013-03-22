package dinosaur.oblig3_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	private static final String DB_NAME = "logger";
	private static final int DB_VERSION = 1;

	public Database(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE log (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"content TEXT NOT NULL, " +
				"details TEXT, " +
				"datetime TEXT NOT NULL, " +
				"category TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS log");
		onCreate(db);
	}

}
