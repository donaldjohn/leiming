package com.leiming.bean;

/**
 * 系统中的常量
 * */
public class Constants {

	// 手势密码点的状态
	public static final int POINT_STATE_NORMAL = 0; // 正常状态

	public static final int POINT_STATE_SELECTED = 1; // 按下状态

	public static final int POINT_STATE_WRONG = 2; // 错误状态
	//用户手势密码sp存储的参数名
	public final static String GESTUREPASS = "GESTUREPASS";
	
	//用户名
    public final static String USER_NAME = "key_name";
	//本地mac地址
    public final static String APP_SYSTEM_MAC = "app_system_mac";
    //当前用户的权限
    public final static String USER_PERMISSION = "permission";
    
    //上传头像使用的常量
    public static final int PHOTO_REQUEST_CAMERA = 2000;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2001;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 2003;// 结果
    public static final String PHOTO_FILE_NAME = "temp_photo.jpg";  //临时保存头像数据的file名称
    
    //本地的SharedPreferences
    public static final String LocalSharedPreferences = "loginUserInfo";
    //用户是否第一次登录的bool
    public static final String ISFIRSTIN = "isFirstIn";
}
