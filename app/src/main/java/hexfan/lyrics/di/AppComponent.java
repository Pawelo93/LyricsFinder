package hexfan.lyrics.di;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 20.06.2017.
 */

@Component(modules = {AppModule.class, RxBusModule.class, AndroidInjectionModule.class, ActivityBuilder.class})
@AppScope
public interface AppComponent{

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MainApplication app);
}

