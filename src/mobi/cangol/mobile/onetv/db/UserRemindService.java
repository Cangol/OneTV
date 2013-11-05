package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.db.model.UserRemind;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class UserRemindService implements BaseService<UserRemind> {
	private static final String TAG = Contants.makeLogTag(UserRemindService.class);
	private Dao<UserRemind, Integer> dao;

	public UserRemindService(Context context) {
		try {
			DataBaseHelper dbHelper=DataBaseHelper.createDataBaseHelper(context);
			dao = dbHelper.getDao(UserRemind.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService init fail!");
		}
	}
	@Override
	public int save(UserRemind obj) {
		int _id=-1;
		try {
			if(obj.get_id()!=null)
				dao.update(obj);
			else
				dao.create(obj);
			 _id=obj.get_id();
		}catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService save fail!");
		}
		return _id;
	}
	@Override
	public void refresh(UserRemind obj)  {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id)  {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService delete fail!");
		}

	}

	@Override
	public UserRemind find(Integer id)  {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService find fail!");
		}
		return null;
	}

	@Override
	public int getCount(){
		try {
		return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService getCount fail!");
		}
		return -1;
	}
	public List<UserRemind> getAllList() {
		try {
			return dao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.e(TAG, "UserRemindService getAllList fail!");
			}
			return null;
	}
	public UserRemind findByVideoId(String videoId)  {
		QueryBuilder<UserRemind, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserRemind> preparedQuery = null;
		try {
				queryBuilder.where().eq("videoId", videoId);
				preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService find fail!");
		}
		return null;
	}
	@Override
	public List<UserRemind> findList(long from, long total) {
		QueryBuilder<UserRemind, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserRemind> preparedQuery = null;
		try {
				queryBuilder.offset(from).limit(total);
				preparedQuery = queryBuilder.prepare();
			return dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserRemindService find fail!");
		}
		return null;
	}
	public UserRemind findByStationId(String stationId)  {
		QueryBuilder<UserRemind, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserRemind> preparedQuery = null;
		try {
			queryBuilder.where().eq("stationId",""+stationId);
			preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "findByStationId  fail!");
		}
		return null;
	}
}
