package hexfan.lyrics.ui.main;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import hexfan.lyrics.di.AppComponent;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.di.DaggerAppComponent;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;

/**
 * Created by Pawel on 20.06.2017.
 */

public class MainApplication extends Application implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

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

        component = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        component.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }
}
