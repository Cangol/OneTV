package mobi.cangol.mobile.onetv.api;

import java.util.HashMap;

public  class ApiContants {
	public static final String URL = "http://192.168.0.101:8080/";
	public static final String URL_STATION_SYNC =URL+ "etweb/api/station/sync.do";
	public static final String URL_STATION_PROGRAM ="http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx/getTVprogramString";
	/**
	 * 
	 * @return
	 */
	public static HashMap<String, String> getBaseParams(){
			HashMap<String, String> map = new HashMap<String, String>();
			return map;
	}

	public static HashMap<String, String> stationSync(String syncTime){
		HashMap<String, String> map = getBaseParams();
		map.put("syncTime",syncTime);
		return map;
	}
	
	public static HashMap<String, String> stationProgram(String theTVchannelID,String theDate,String userId){
		HashMap<String, String> map = getBaseParams();
		map.put("theTVchannelID",theTVchannelID);
		map.put("theDate",theDate);
		map.put("userID",userId);
		return map;
	}
}
