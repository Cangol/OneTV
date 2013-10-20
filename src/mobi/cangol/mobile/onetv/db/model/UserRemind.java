/** 
 * Copyright (c) 2013 Cangol.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mobi.cangol.mobile.onetv.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Description UserRemind.java 
 * @author Cangol
 * @date 2013-9-8
 */
@DatabaseTable(tableName="user_remind")
public class UserRemind  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
	@DatabaseField(generatedId = true,unique=true)  
	private Integer _id;
	@DatabaseField  
	private String id;
	@DatabaseField  
	private String lastPlayTime;
	@DatabaseField 
	private String stationId;
	@DatabaseField  
	private String stationUrl;
	@DatabaseField  
	private String stationName;
	
	public UserRemind(){
	}

	/**
	 * Creates a new instance of UserRemind. 
	 * @param id
	 * @param lastPlayTime
	 * @param stationId
	 * @param stationUrl
	 * @param stationName
	 */
	public UserRemind(String id, String lastPlayTime, String stationId,
			String stationUrl, String stationName) {
		super();
		this.id = id;
		this.lastPlayTime = lastPlayTime;
		this.stationId = stationId;
		this.stationUrl = stationUrl;
		this.stationName = stationName;
	}

	/**
	 * @return the _id
	 */
	public Integer get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(Integer _id) {
		this._id = _id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the lastPlayTime
	 */
	public String getLastPlayTime() {
		return lastPlayTime;
	}

	/**
	 * @param lastPlayTime the lastPlayTime to set
	 */
	public void setLastPlayTime(String lastPlayTime) {
		this.lastPlayTime = lastPlayTime;
	}

	/**
	 * @return the stationId
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the stationUrl
	 */
	public String getStationUrl() {
		return stationUrl;
	}

	/**
	 * @param stationUrl the stationUrl to set
	 */
	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	
	
}
