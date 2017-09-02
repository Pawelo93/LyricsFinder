package hexfan.lyrics.di;

import dagger.Component;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.main.MainActivity;

/**
 * Created by Pawel on 20.06.2017.
 */

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);
}
