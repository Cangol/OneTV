package mobi.cangol.mobile.onetv.api;

import java.util.List;
import java.util.Vector;

public class WebServicesFeed extends Feed{
	public String string;
	public List<String> data;
	public WebServicesFeed(){
		data=new Vector<String>(0);
	}
	public void addItem(String item){
		data.add(item);
	}
	public List<String> getAllData() {
		return data;
	}
	public String getData(int location){
		return data.get(location);
	}
}
