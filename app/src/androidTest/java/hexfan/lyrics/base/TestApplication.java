package hexfan.lyrics.base;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import javax.inject.Inject;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import hexfan.lyrics.di.AppComponent;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.di.AppScope;
import hexfan.lyrics.di.DaggerAppComponent;
import hexfan.lyrics.di.RxBusModule;
import hexfan.lyrics.ui.browse.BrowseFragmentTest;
import hexfan.lyrics.ui.main.MainActivityTest;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 18.11.2017.
 */

public class TestApplication extends MainApplication {

//    @Inject
//    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
//    @Inject
//    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

//
//    @Component(modules = {AppModule.class, RxBusModule.class})
//    public interface TestAppComponent extends AppComponent{
//
//        @Component.Builder
//        interface Builder {
//            @BindsInstance
//            TestAppComponent.Builder application(Application application);
//            TestAppComponent build();
//        }
//
//        void inject(TestApplication app);
//    }

//    public static TestApplication INSTANCE;

    @Override
    public void onCreate() {

//        INSTANCE = this;
//        component = DaggerTestAppComponent
//                .builder()
//                .application(this)
//                .build();

//        component.inject(this);
    }

//    @Override
//    public AndroidInjector<Activity> activityInjector() {
//        return activityDispatchingAndroidInjector;
//    }
//
//    @Override
//    public AndroidInjector<Service> serviceInjector() {
//        return serviceDispatchingAndroidInjector;
//    }
}