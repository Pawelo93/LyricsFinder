package hexfan.lyrics.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Pawel on 06.09.2017.
 */

@Module
public class RxBusModule {

    @Provides
    @AppScope
    BehaviorSubject<TrackInfo> trackInfoBehaviorSubject(){
        return BehaviorSubject.create();
    }

    @Provides
    @AppScope
    PublishSubject<String> nowListenBus(){
        return PublishSubject.create();
    }
}
