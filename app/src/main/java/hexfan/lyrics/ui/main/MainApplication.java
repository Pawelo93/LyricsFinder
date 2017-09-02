package hexfan.lyrics.ui.main;

import android.app.Application;
import android.content.Intent;
import android.test.mock.MockApplication;

import hexfan.lyrics.MyService;
import hexfan.lyrics.di.AppComponent;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.di.DaggerAppComponent;
import hexfan.lyrics.ui.base.BaseActivity;

/**
 * Created by Pawel on 20.06.2017.
 */

public class MainApplication extends Application {

    AppComponent appComponent;

    public static MainApplication get(BaseActivity baseActivity) {
        return ((MainApplication) baseActivity.getApplication());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
