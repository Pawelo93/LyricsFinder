package hexfan.lyrics.ui.main;

import android.app.Application;

import hexfan.lyrics.di.AppComponent;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.di.DaggerAppComponent;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;

/**
 * Created by Pawel on 20.06.2017.
 */

public class MainApplication extends Application{

    public static MainApplication INSTANCE;
    public AppComponent component;

    public static MainApplication get(BaseActivity baseActivity) {
        return ((MainApplication) baseActivity.getApplication());
    }

    public static MainApplication get(BaseFragment baseFragment) {
        return ((MainApplication) baseFragment.getActivity().getApplication());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);

    }
}
