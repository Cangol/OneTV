package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.UserFavorite;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class UserFavoriteService implements BaseService<UserFavorite> {
	private static final String TAG = Contants.makeLogTag(UserFavoriteService.class);
	private Dao<UserFavorite, Integer> dao;

	public UserFavoriteService(Context context) {
		try {
			DataBaseHelper dbHelper=DataBaseHelper.createDataBaseHelper(context);
			dao = dbHelper.getDao(UserFavorite.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService init fail!");
		}
	}
	@Override
	public int save(UserFavorite obj) {
		int _id=-1;
		try {
			if(obj.get_id()!=null)
				_id=dao.update(obj);
			else
				_id=dao.create(obj);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService save fail!");
		}
		return _id;
	}
	@Override
	public void refresh(UserFavorite obj)  {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id)  {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService delete fail!");
		}

	}

	@Override
	public UserFavorite find(Integer id)  {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService find fail!");
		}
		return null;
	}

	@Override
	public int getCount(){
		try {
		return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService getCount fail!");
		}
		return -1;
	}
	public List<UserFavorite> getAllList() {
		try {
			return dao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.e(TAG, "UserFavoriteService getAllList fail!");
			}
			return null;
	}
	public UserFavorite findByVideoId(String videoId)  {
		QueryBuilder<UserFavorite, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<UserFavorite> preparedQuery = null;
		try {
				queryBuilder.where().eq("videoId", videoId);
				preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "UserFavoriteService find fail!");
		}
		return null;
	}
}
