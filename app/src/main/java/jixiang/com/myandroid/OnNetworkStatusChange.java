package jixiang.com.myandroid;


public interface OnNetworkStatusChange{
	/**
	 * 网络类型改变
	 * @param isAviable 网络是否可用，true 可用
	 * @param type 网络类型  （网络不可用 type -1）
	 */
	void onNetWorkStatusChanged(boolean isAviable, int type);
}
