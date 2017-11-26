package hexfan.lyrics.base;

import dagger.Component;
import hexfan.lyrics.di.AppComponent;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.di.AppScope;
import hexfan.lyrics.di.RxBusModule;
import hexfan.lyrics.ui.browse.BrowseFragmentTest;
import hexfan.lyrics.ui.main.MainActivityTest;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 18.11.2017.
 */

public class TestApplication extends MainApplication {

    @Component(modules = {AppModule.class, RxBusModule.class})
    @AppScope
    public interface TestComponent extends AppComponent{

        void inject(TestApplication testApplication);

        MainActivityTest.TestMainActivityComponent addComponent(MainActivityTest.TestMainActivityModule mainActivityModule);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        component = DaggerTestApplication_TestComponent.builder().appModule(new AppModule(this)).build();
    }
}