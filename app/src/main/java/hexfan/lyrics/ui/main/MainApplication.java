package hexfan.lyrics.ui.main;

import android.app.Application;
import android.test.mock.MockApplication;

import hexfan.lyrics.di.DaggerMyComponents;
import hexfan.lyrics.di.MyComponents;
import hexfan.lyrics.di.AppModule;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.browse.BrowseFragment;

/**
 * Created by Pawel on 20.06.2017.
 */

public class MainApplication extends Application {

    MyComponents myComponents;

    public static MainApplication get(BaseActivity baseActivity) {
        return ((MainApplication) baseActivity.getApplication());
    }

    public static MainApplication get(BaseFragment baseFragment) {
        return ((MainApplication) baseFragment.getActivity().getApplication());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myComponents = DaggerMyComponents.builder().appModule(new AppModule(this)).build();

    }

    public MyComponents getMyComponents(){
        return myComponents;
    }

}
