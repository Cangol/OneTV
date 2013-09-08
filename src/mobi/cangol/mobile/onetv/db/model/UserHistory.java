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
 * @Description UserHistory.java 
 * @author Cangol
 * @date 2013-9-8
 */
@DatabaseTable(tableName="user_history")
public class UserHistory  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
	@DatabaseField(generatedId = true,unique=true)  
	private Integer _id;
	@DatabaseField  
	private String id;
	@DatabaseField  
	private String videoId;
	@DatabaseField  
	private String videoName;
	@DatabaseField  
	private String lastPlayTime;
	public UserHistory(){
	}
	/**
	 * Creates a new instance of UserHistory. 
	 * @param _id
	 * @param id
	 * @param videoId
	 * @param videoName
	 * @param lastPlayTime
	 */
	public UserHistory(Integer _id, String id, String videoId, String videoName,
			String lastPlayTime) {
		super();
		this._id = _id;
		this.id = id;
		this.videoId = videoId;
		this.videoName = videoName;
		this.lastPlayTime = lastPlayTime;
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
	 * @return the videoId
	 */
	public String getVideoId() {
		return videoId;
	}
	/**
	 * @param videoId the videoId to set
	 */
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	/**
	 * @return the videoName
	 */
	public String getVideoName() {
		return videoName;
	}
	/**
	 * @param videoName the videoName to set
	 */
	public void setVideoName(String videoName) {
		this.videoName = videoName;
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
	
}
