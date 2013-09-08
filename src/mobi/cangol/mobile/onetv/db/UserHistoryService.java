package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.UserHistory;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class UserHistoryService implements BaseService<UserHistory> {
	private static final String TAG = Contants.makeLogTag(UserHistoryService.class);
	private Dao<UserHistory, Integer> dao;

	public UserHistoryService(Context context) {
		try {
			DataBaseHelper dbHelper=DataBaseHelper.createDataBaseHelper(context);
			dao = dbHelper.getDao(UserHistory.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService init fail!");
		}
	}
	@Override
	public int save(UserHistory obj) {
		int _id=-1;
		try {
			if(obj.get_id()!=null)
				_id=dao.update(obj);
			else
				_id=dao.create(obj);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService save fail!");
		}
		return _id;
	}
	@Override
	public void refresh(UserHistory obj)  {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id)  {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService delete fail!");
		}

	}

	@Override
	public UserHistory find(Integer id)  {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService find fail!");
		}
		return null;
	}

	@Override
	public int getCount(){
		try {
		return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService getCount fail!");
		}
		return -1;
	}
	public List<UserHistory> getAllList() {
		try {
			return dao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.e(TAG, "UserHistoryService getAllList fail!");
			}
			return null;
	}
	public UserHistory findByVideoId(String videoId)  {
		QueryBuilder<UserHistory, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserHistory> preparedQuery = null;
		try {
				queryBuilder.where().eq("videoId", videoId);
				preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserHistoryService find fail!");
		}
		return null;
	}
}