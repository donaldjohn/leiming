package com.leiming.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 负责进行加载
 * */
@Deprecated
public class LoadTitlesDataService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
