package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.UserFollow;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class UserFollowService implements BaseService<UserFollow> {
	private static final String TAG = Contants.makeLogTag(UserFollowService.class);
	private Dao<UserFollow, Integer> dao;

	public UserFollowService(Context context) {
		try {
			DataBaseHelper dbHelper=DataBaseHelper.createDataBaseHelper(context);
			dao = dbHelper.getDao(UserFollow.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService init fail!");
		}
	}
	@Override
	public int save(UserFollow obj) {
		int _id=-1;
		try {
			if(obj.get_id()!=null)
				_id=dao.update(obj);
			else
				_id=dao.create(obj);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService save fail!");
		}
		return _id;
	}
	@Override
	public void refresh(UserFollow obj)  {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id)  {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService delete fail!");
		}

	}

	@Override
	public UserFollow find(Integer id)  {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService find fail!");
		}
		return null;
	}

	@Override
	public int getCount(){
		try {
		return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService getCount fail!");
		}
		return -1;
	}
	public List<UserFollow> getAllList() {
		try {
			return dao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.e(TAG, "UserFollowService getAllList fail!");
			}
			return null;
	}
	public UserFollow findByVideoId(String videoId)  {
		QueryBuilder<UserFollow, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserFollow> preparedQuery = null;
		try {
				queryBuilder.where().eq("videoId", videoId);
				preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFollowService find fail!");
		}
		return null;
	}
}
