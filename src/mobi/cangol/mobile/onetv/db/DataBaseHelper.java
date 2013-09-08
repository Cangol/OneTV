package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;

import mobi.cangol.mobile.onetv.db.model.UserFavorite;
import mobi.cangol.mobile.onetv.db.model.UserFollow;
import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.db.model.VideoTv;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = "DataBaseHelper";
	private static final String DATABASE_NAME = Contants.DATABASE_NAME;
	private static final int DATABASE_VERSION = Contants.DATABASE_VERSION;
	private static DataBaseHelper dataBaseHelper;
	public static DataBaseHelper createDataBaseHelper(Context context){
		if(null==dataBaseHelper){	
			dataBaseHelper=new DataBaseHelper(context);
		}
		return dataBaseHelper;
	}
	private DataBaseHelper(Context context) {
		super(context,DATABASE_NAME, null, DATABASE_VERSION);
	}

	private DataBaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {

		try {
			
			TableUtils.createTable(connectionSource, UserFavorite.class);
			TableUtils.createTable(connectionSource, UserFollow.class);
			TableUtils.createTable(connectionSource, UserHistory.class);
			TableUtils.createTable(connectionSource, VideoTv.class);
		} catch (java.sql.SQLException e) {
			Log.e(TAG, "create database fail", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		try {
			if(db.getVersion()<DATABASE_VERSION){
				TableUtils.dropTable(connectionSource, UserFavorite.class, true);
				TableUtils.dropTable(connectionSource, UserFollow.class, true);
				TableUtils.dropTable(connectionSource, UserHistory.class, true);
				TableUtils.dropTable(connectionSource, VideoTv.class, true);
				onCreate(db, connectionSource);
			}
		} catch (SQLException e) {
			Log.e(TAG, "upgrade database fail", e);
			e.printStackTrace();
		}
	}
}
