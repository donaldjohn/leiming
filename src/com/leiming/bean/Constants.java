package com.leiming.bean;

/**
 * ϵͳ�еĳ���
 * */
public class Constants {

	// ����������״̬
	public static final int POINT_STATE_NORMAL = 0; // ����״̬

	public static final int POINT_STATE_SELECTED = 1; // ����״̬

	public static final int POINT_STATE_WRONG = 2; // ����״̬
	//�û���������sp�洢�Ĳ�����
	public final static String GESTUREPASS = "GESTUREPASS";
	
	//�û���
    public final static String USER_NAME = "key_name";
	//����mac��ַ
    public final static String APP_SYSTEM_MAC = "app_system_mac";
    //��ǰ�û���Ȩ��
    public final static String USER_PERMISSION = "permission";
    
    //�ϴ�ͷ��ʹ�õĳ���
    public static final int PHOTO_REQUEST_CAMERA = 2000;// ����
    public static final int PHOTO_REQUEST_GALLERY = 2001;// �������ѡ��
    public static final int PHOTO_REQUEST_CUT = 2003;// ���
    public static final String PHOTO_FILE_NAME = "temp_photo.jpg";  //��ʱ����ͷ�����ݵ�file����
    
    //���ص�SharedPreferences
    public static final String LocalSharedPreferences = "loginUserInfo";
    //�û��Ƿ��һ�ε�¼��bool
    public static final String ISFIRSTIN = "isFirstIn";
}
