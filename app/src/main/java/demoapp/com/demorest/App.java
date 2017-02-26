package demoapp.com.demorest;

import android.content.Context;

import com.quickblox.sample.core.CoreApp;

import demoapp.com.demorest.utils.Consts;
import demoapp.com.demorest.utils.QBResRequestExecutor;

public class App extends CoreApp {
    private static App instance;
    private QBResRequestExecutor qbResRequestExecutor;

    protected static Context context = null;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        context = getApplicationContext();
    }

    private void initApplication(){
        instance = this;
        super.initCredentials(Consts.APP_ID, Consts.AUTH_KEY, Consts.AUTH_SECRET, Consts.ACCOUNT_KEY);
    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }
    public static Context getContext() {
        return context;
    }
}
