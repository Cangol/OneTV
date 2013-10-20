package mobi.cangol.mobile.onetv.api;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WebServicesHandler extends MySAXHandler {
	private WebServicesFeed  mFeed;
	private String string;
	private StringBuffer sb = new StringBuffer();
	private final int DATA_STRING = 1;

	private int currentstate = 0;
	@Override
	public WebServicesFeed getFeed() {
		return mFeed;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String theString = new String(ch, start, length);
		sb.append(theString);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		switch (currentstate) {
			case DATA_STRING:
				string=sb.toString();
				currentstate = 0;
				break;
			default:
				currentstate = 0;
				return;
		}
		if (localName.equals("string")) {
			mFeed.addItem(string);
			return;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		mFeed=new WebServicesFeed();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		sb = new StringBuffer();
		if(localName.equals("string")){
			currentstate = DATA_STRING;
			return;
		}
		currentstate = 0;
	}
	
}
