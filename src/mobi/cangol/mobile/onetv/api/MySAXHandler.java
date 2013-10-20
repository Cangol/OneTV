package mobi.cangol.mobile.onetv.api;

import org.xml.sax.helpers.DefaultHandler;


public abstract class MySAXHandler extends DefaultHandler{
	public abstract Feed getFeed();
}
