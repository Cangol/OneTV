package mobi.cangol.mobile.onetv.api;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.cangol.mobile.json.JsonUtils;

public class ApiHttpResult<T> implements Serializable{
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String source;
	private String error;
	private T object;
	private List<T> list;
	public final static  String SUCCESS = "success";
	public final static  String ERROR = "result";
	public final static  String RESULT = "result";
	public final static  int SUCCESS_200 = 200;//操作成功
	public final static  int FAILURE_201 = 201;//操作失败 
	public final static  int FAILURE_202 = 202;//Sign不对
	public final static  int FAILURE_104 = 404;//请求参数正常或缺少参数
	public final static  int FAILURE_500 = 500;//非法请求
	private ApiHttpResult(){}
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public static <T> ApiHttpResult<T> parserObject(Class<T> c,JSONObject json) {
		ApiHttpResult<T> result = new ApiHttpResult<T>();
		try {
			result.setSource(json.toString());
			result.setSuccess(json.getBoolean(SUCCESS));
			if(result.isSuccess()&&c!=null) {
				Object resultObject=json.get(RESULT);
				if(resultObject instanceof JSONObject){	
					result.setObject(JsonUtils.parserToObjectByAnnotation(c, json.getJSONObject(RESULT)));
				}else{
					result.setList(JsonUtils.parserToList(c, json.getJSONArray(RESULT),true));
				}
			}else{
				String error = json.getString(ERROR);
				result.setError(error);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
