package app.auto.runner.base.framework;

import android.content.Context;

import app.auto.Base;
import app.auto.runner.base.DipUtil;
import app.auto.runner.base.SPrefUtil;
import app.auto.runner.base.action.Epr;
import app.auto.runner.base.task.AsyncClient;

/***
 * android 入口全局类
 * @author Administrator
 *
 */
public class Init extends Base {
	// start: Enviroment market
	public static AsyncClient asyncClient;
	public static Context bigContext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		asyncClient = AsyncClient.getInstane();
		
		bigContext = this;

//		MapViewConf.initDefaultInmg(R.mipmap.default_img);
		SPrefUtil.iniContext(this);
		
		DipUtil.initCtx(this);
		Epr.setCtx(this);
	}

	


}
