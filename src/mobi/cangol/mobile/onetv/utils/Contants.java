package mobi.cangol.mobile.onetv.utils;

public class Contants {
	public static final boolean DEBUG=true;
	public static final boolean FREEUSER=false;
	public static final int DATABASE_VERSION=1;
	public static final String DATABASE_NAME="OneTv";
	public static final String SHARED="OneTv";
	public static final String TAG="OneTv_";
	public static final String APP="/OneTv";
	public static final String APP_TEMP=APP+"/temp";
	public static final String APP_DB="OneTv.db";
	
	public static final String SPECIAL_IMEI="000000000000000";
	public static final String SPECIAL_ANDROID_ID="9774d56d682e549c";
	
	/**application session key**/
	public static final String VERSION = "version";
	public static final String DEVICEID = "deviceId";
	public static final String DEVICETYPE = "deviceType";
	public static final String UID = "uid";
	public static final String NETWORKTYPE = "networktype";
	
	
	public static String makeLogTag(Class cls) {
		//String tag = TAG + cls.getSimpleName();
		String tag = TAG ;
		return (tag.length() > 23) ? tag.substring(0, 23) : tag;
	}
}
