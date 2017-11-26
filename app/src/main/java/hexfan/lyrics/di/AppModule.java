package hexfan.lyrics.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import hexfan.lyrics.model.ApiManager;
import hexfan.lyrics.model.DataManager;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.DatabaseDataManager;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.db.AppDatabase;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Pawel on 20.06.2017.
 */

@Module
public class AppModule {

    @Provides
    @AppScope
    Context providesContext(Application application){
        return application.getApplicationContext();
    }

    @Provides
    @AppScope
    Picasso providePicasso(Application application) {
        return new Picasso.Builder(application).build();
    }

    @Provides
    @AppScope
    SharedPreferences providesSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }


    @Provides
    @AppScope
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter)
        return gsonBuilder.create();
    }

    @Provides
    @AppScope
    ApiManager provideApiManager(Retrofit retrofit){
        return retrofit.create(ApiManager.class);
    }

    @Provides
    @AppScope
    DataModel provideMainDataModel(ApiManager apiManager, BehaviorSubject<TrackInfo> bus){
        return new DataManager(apiManager, bus);
    }

    @Provides
    @AppScope
    DatabaseDataModel provideDatabaseDataModel(AppDatabase database){
        return new DatabaseDataManager(database);
    }

    @Provides
    @AppScope
    Cache providesHttpCache(Application application){
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(Cache cache) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

//        client.networkInterceptors().add(new CachingControlInterceptor());

        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Provides
    @AppScope
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://www.tekstowo.pl/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @AppScope
    AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getDatabase(context);
    }
}
