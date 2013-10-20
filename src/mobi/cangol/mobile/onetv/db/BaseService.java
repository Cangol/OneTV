package mobi.cangol.mobile.onetv.db;
/**
 * 
 * @Description: 
 * @version $Revision: 2.0 $ 
 * @author xuewu.wei
 * @date: 2010-12-6
 * @time: 10:53:36
 */
import java.sql.SQLException;
import java.util.List;

import mobi.cangol.mobile.onetv.db.model.UserHistory;



public interface  BaseService<T> {
	
	/**
	 * 
	 * @param obj
	 * @return 
	 */
	void refresh(T obj) throws SQLException ;
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id) throws SQLException ;
	/**
	 * 
	 * @param id
	 * @return
	 */
	T find(Integer id) throws SQLException ;
	/**
	 * 
	 * @return
	 */
	int getCount() throws SQLException ;
	/**
	 * 
	 * @param keywords
	 * @return
	 */
	List<T> getAllList() throws SQLException ;
	/**
	 * 
	 * @param obj
	 */
	int save(T obj);
	/**
	 * 
	 * @param from
	 * @param total
	 * @return
	 */
	List<T> findList(long from,long total) ;
}
