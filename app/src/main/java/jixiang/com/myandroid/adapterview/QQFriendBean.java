package jixiang.com.myandroid.adapterview;

import java.io.Serializable;

public class QQFriendBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int header_icon;
	public String name;
	public String sign;
	//是否在线
	public boolean isOnLine;
	
	public QQFriendBean() {
		// TODO Auto-generated constructor stub
	}
	
	public QQFriendBean(int headerIcon, String name, String sign, boolean isOnLine) {
		this.header_icon = headerIcon;
		this.name = name;
		this.sign = sign;
		this.isOnLine = isOnLine;
	}

}
