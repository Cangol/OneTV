package mobi.cangol.mobile.onetv.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class CommSAXParserUtil {
	public static final int WEBSERVICES_HANDLER = 1;		//
	
	private SAXParserFactory factory;
	private SAXParser parser;
	private MySAXHandler handler;
	private int handlerName;
	
	public CommSAXParserUtil() {
			try {
				factory = SAXParserFactory.newInstance();
				parser = factory.newSAXParser();
			} catch (FactoryConfigurationError e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}	
	}
	
	public Feed getFeed(int theHandler,InputStream in){
		handlerName = theHandler;
		this.handler = getSAXHandler();
		try {
			parser.parse(in, this.handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getFeed();
	}
	public Feed getFeed(int theHandler,String str){
		InputStream   in   =   new   ByteArrayInputStream(str.getBytes());   
		handlerName = theHandler;
		this.handler = getSAXHandler();
		try {
			parser.parse(in, this.handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getFeed();
	}
	private MySAXHandler getSAXHandler(){
		MySAXHandler handler = null;
		switch(handlerName){
			case WEBSERVICES_HANDLER:
				handler = new WebServicesHandler();
				break;
			default :
				break;
		}
		return handler;
	}
}
