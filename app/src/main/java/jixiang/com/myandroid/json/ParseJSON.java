package jixiang.com.myandroid.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import jixiang.com.myandroid.adapterview.QQFriendBean;

public class ParseJSON extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*parse();
		parseObj();
		parseArray();*/
		autoParse();
	}

	private void parse() {
		String str = "{\"firstName\":\"Brett\",\"lastName\":\"McLaughlin\",\"email\":\"aaaa@qq.com\", \"middle\":null}";
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String firstName = "";
		try {
			firstName = jsonObject.getString("firstName");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/*
		 * String name, key String fallback 浠呭綋杩欎釜閿�煎涓嶅瓨鍦ㄧ殑鏃跺�欙紝鎵嶄細杩斿洖杩欎釜鍊�
		 */
		String middleName = jsonObject.optString("middle", "AAAAAAAAAAAA");
		String lastName = jsonObject.optString("lastName", "");
		String email = jsonObject.optString("email", "");
		System.out.println("firstName=" + firstName + ", lastName=" + lastName
				+ ", email =" + email + ", middle=" + middleName);
		TextView textView = new TextView(this);
		textView.setText("firstName=" + firstName + ", lastName=" + lastName
				+ ", email =" + email + ", middle=" + middleName);
		setContentView(textView);
	}

	private void parseObj() {
		String str = "{\"error\":0,\"result\":0,\"user\":{\"user_id\":\"1\",\"user_name\":\"administrator\"},\"msg\":\"\\u6ce8\\u518c\\u7528\\u6237\\u6210\\u529f\"}";
		try {
			JSONObject jsonObject = new JSONObject(str);
			JSONObject userObject = jsonObject.optJSONObject("user");
			int id = userObject.optInt("user_id", 0);
			String msg = jsonObject.optString("msg", "");
			Toast.makeText(this, "id=" + id + ", msg=" + msg,
					Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void parseArray() {
//		String str = "{\"error\": 0,\"result\": 0,\"list\": [{\"pas_id\": \"1\",\"pas_name\": \"鎮ㄧ埗浜茬殑鍚嶅瓧锛焅"},{\"pas_id\": \"2\",\"pas_name\": \"鎮ㄦ瘝浜茬殑鍚嶅瓧锛焅"},{\"pas_id\": \"13\",\"pas_name\": \"鎮ㄧ涓�鍙扮數鑴戠殑鍚嶇О锛焅"}],\"msg\": \"鑾峰彇瀵嗕繚闂鎴愬姛\"}";
//		try {
//			JSONObject jsonObject = new JSONObject(str);
//			JSONArray jsonArray = jsonObject.optJSONArray("list");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				
//
//				JSONObject object = jsonArray.optJSONObject(i);
//				int passId = object.optInt("pas_id", 0);
//				String pasName = object.optString("pas_name", "");
//				System.out.println("pas_id=" + passId + ", pasName=" + pasName);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		final List<QQFriendBean> list = new ArrayList<QQFriendBean>();
//		QQFriendBean bean = new QQFriendBean();
//		// for (int i = 0; i < 100; i++) {
//
//		bean.header_icon = R.drawable.a005;
//		bean.name = "鍒樺痉鍗�";
//		bean.sign = "璋佹妸鎴戝効瀛愭姠浜嗭紵";
//		list.add(bean);
//		bean = new QQFriendBean();
//		bean.header_icon = R.drawable.a006;
//		bean.name = "寮犻煻娑�";
//		bean.sign = "淇℃槬鍝ュ緱姘哥敓锛�";
//		list.add(bean);
//		bean = new QQFriendBean();
//		bean.header_icon = R.drawable.a007;
//		bean.name = "寮犲鍙�";
//		bean.sign = "鑿婅姳鏈垫湹寮�!";
//		list.add(bean);
//		bean = new QQFriendBean();
//		bean.header_icon = R.drawable.a008;
//		bean.name = "寮犳棤蹇�";
//		bean.sign = "鍊氬ぉ灞犻緳璁帮紵";
//		list.add(bean);
//		bean = new QQFriendBean();
//		bean.header_icon = R.drawable.a009;
//		bean.name = "鏂摝杈涙牸";
//		bean.sign = "Suck it锛�";
//		list.add(bean);
//
//		System.out.println(createJsonString(list));

//		List<QQFriendBean> mList = parseJsonArray(createJsonString(list));
//		for (int i = 0; i < mList.size(); i++) {
//			QQFriendBean bean2 = mList.get(i);
//			System.out.println("header_icon=" + bean2.header_icon);
//			System.out.println("name=" + bean2.name);
//			System.out.println("sign=" + bean.sign);
		}
//	}

	public String createJsonString(Object value) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(value);
		return jsonStr;
	}

	public List<QQFriendBean> parseJsonArray(String jsonStr) {
		Gson gson = new Gson();
		List<QQFriendBean> list = gson.fromJson(jsonStr,
				new TypeToken<List<QQFriendBean>>() {
				}.getType());
		return list;
	}

	public static class Person {
		public String name;
		public int age;
		@Override
		public String toString() {
			System.out.println("[name= " + name + ", age=" + age + "]");
			return "[name= " + name + ", age=" + age + "]";
		}
	}

	public void autoParse() {
		/*Person person = new Person();
		person.name = "zhangsan";
		person.age = 20;*/
		String jsonStr = "{\"name\":\"zhangsan\", \"age\":20}";
		Person person2 = autoParse(jsonStr, Person.class);
		//System.out.println(person2);
	}

	public <T>  T autoParse(String jsonStr, Class<T> cls) {
		// 鍏堟嬁鍒拌繖涓被鐨勬墍鏈夌殑瀛楁
		try {
			T obj = cls.newInstance(); //鍒濆鍖栦竴涓璞�
			Field[] filds = obj.getClass().getDeclaredFields();//鑾峰彇杩欎釜绫讳笅闈㈢殑鎵�鏈夌殑瀛楁
			Method[] medhtds = obj.getClass().getDeclaredMethods(); //鎷垮埌杩欎釜绫讳笅闈㈢殑鎵�鏈夌殑鏂规硶
			for(int i=0; i< medhtds.length; i++) {
				System.out.println("method name=" + medhtds[i].getName());
			}
			Method method = obj.getClass().getDeclaredMethod("toString");
			for (int i = 0; i < filds.length; i++) {
				String fildName = filds[i].getName();
				Class cl = filds[i].getType();
				System.out.println("cl=" + cl.getName());
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(jsonStr);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String str = "";
				int in;
				if (cl.getName().equals(String.class.getName())) {
					str = jsonObject.optString(fildName, "");
					System.out.println("str=" + str);
					filds[i].set(obj, str); //obj.name= str;
				} else if (cl.getName().equals("int")) {
					in = jsonObject.optInt(fildName, 0);
					System.out.println("in=" + in);
					filds[i].setInt(obj, in); //obj.age = in;
				}
			}
			method.invoke(obj); //璋冪敤obj.toString();
			return  obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
