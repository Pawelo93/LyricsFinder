package hexfan.lyrics.ui.browse;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import java.util.List;

import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.spotify.SpotifyModel;
import hexfan.lyrics.ui.main.MainViewModel;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Pawel on 15.11.2017.
 */

public class BrowseViewModel extends ViewModel {
    private DatabaseDataModel databaseModel;

    public BrowseViewModel(DatabaseDataModel databaseModel) {
        this.databaseModel = databaseModel;
    }

    public Flowable<List<TrackInfo>> observeLastTrackInfos(){
        return databaseModel.getAllTrackInfoFromCache()
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Helping create view model with custom constructor
     */
    public static class Factory extends ViewModelProviders.DefaultFactory {

        DatabaseDataModel databaseModel;

        /**
         * Creates a {@code DefaultFactory}
         *
         * @param application an application to pass in {@link MainViewModel}
         */
        public Factory(@NonNull Application application, DatabaseDataModel databaseModel) {
            super(application);
            this.databaseModel = databaseModel;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new BrowseViewModel(databaseModel);
        }
    }
}
