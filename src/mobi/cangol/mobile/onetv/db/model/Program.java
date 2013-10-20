package mobi.cangol.mobile.onetv.db.model;

public class Program {
	private String playTime;
	private String meridiem;
	private String tvProgram;
	private String tvStationInfo;
	private String channel;
	
	/**
	 * @param playTime
	 * @param meridiem
	 * @param tvProgram
	 * @param tvStationInfo
	 * @param channel
	 */
	public Program(String playTime, String meridiem, String tvProgram,
			String tvStationInfo) {
		super();
		this.playTime = playTime;
		this.meridiem = meridiem;
		this.tvProgram = tvProgram;
		this.tvStationInfo = tvStationInfo;
	}
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * @return the playTime
	 */
	public String getPlayTime() {
		return playTime;
	}
	/**
	 * @param playTime the playTime to set
	 */
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	/**
	 * @return the meridiem
	 */
	public String getMeridiem() {
		return meridiem;
	}
	/**
	 * @param meridiem the meridiem to set
	 */
	public void setMeridiem(String meridiem) {
		this.meridiem = meridiem;
	}
	/**
	 * @return the tvProgram
	 */
	public String getTvProgram() {
		return tvProgram;
	}
	/**
	 * @param tvProgram the tvProgram to set
	 */
	public void setTvProgram(String tvProgram) {
		this.tvProgram = tvProgram;
	}
	/**
	 * @return the tvStationInfo
	 */
	public String getTvStationInfo() {
		return tvStationInfo;
	}
	/**
	 * @param tvStationInfo the tvStationInfo to set
	 */
	public void setTvStationInfo(String tvStationInfo) {
		this.tvStationInfo = tvStationInfo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Program [channel=" + channel + ", meridiem=" + meridiem
				+ ", playTime=" + playTime + ", tvProgram=" + tvProgram
				+ ", tvStationInfo=" + tvStationInfo + "]";
	}
	
	
}
