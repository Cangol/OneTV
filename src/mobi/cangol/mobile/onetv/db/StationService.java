package mobi.cangol.mobile.onetv.db;

import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.Station;
import mobi.cangol.mobile.onetv.utils.Contants;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class StationService implements BaseService<Station> {
	private static final String TAG = Contants.makeLogTag(StationService.class);
	private Dao<Station, Integer> dao;

	public StationService(Context context) {
		try {
			DataBaseHelper dbHelper=DataBaseHelper.createDataBaseHelper(context);
			dao = dbHelper.getDao(Station.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService init fail!");
		}
	}
	@Override
	public int save(Station obj) {
		int _id=-1;
		try {
			if(obj.get_id()!=null)
				_id=dao.update(obj);
			else
				_id=dao.create(obj);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService save fail!");
		}
		return _id;
	}
	@Override
	public void refresh(Station obj)  {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id)  {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService delete fail!");
		}

	}

	@Override
	public Station find(Integer id)  {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService find fail!");
		}
		return null;
	}

	@Override
	public int getCount(){
		try {
		return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService getCount fail!");
		}
		return -1;
	}
	public List<Station> getAllList() {
		try {
			return dao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.e(TAG, "ServiceService getAllList fail!");
			}
			return null;
	}
	public Station findByStationoId(String id)  {
		QueryBuilder<Station, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<Station> preparedQuery = null;
		try {
				queryBuilder.where().eq("id", id);
				preparedQuery = queryBuilder.prepare();
			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService find fail!");
		}
		return null;
	}
	@Override
	public List<Station> findList(long from, long total) {
		QueryBuilder<Station, Integer> queryBuilder = dao.queryBuilder();
		PreparedQuery<Station> preparedQuery = null;
		try {
				queryBuilder.offset(from).limit(total);
				preparedQuery = queryBuilder.prepare();
			return dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "ServiceService find fail!");
		}
		return null;
	}
}
