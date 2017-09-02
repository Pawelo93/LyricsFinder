package hexfan.lyrics.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Unbinder;
import hexfan.lyrics.model.AppDataManager;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 20.06.2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected AppDataManager dataModel;
    @Inject
    protected Picasso picasso;
    @Inject
    protected Gson gson;

    private List<Unbinder> unbinders = new ArrayList<>();
    protected boolean isVisable = true;

    public static BaseActivity get(BaseFragment baseFragment) {
        return ((BaseActivity) baseFragment.getActivity());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainApplication.get(this).getAppComponent().inject(this);

    }

    protected void setUnbinder(Unbinder unbinder){
        unbinders.add(unbinder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisable = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisable = false;
    }

    @Override
    protected void onDestroy() {
        if (unbinders.size() > 0) {
            for(Unbinder unbinder : unbinders)
                unbinder.unbind();
        }
        super.onDestroy();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public Gson getGson() {
        return gson;
    }

    public DataModel getDataModel(){
        return dataModel;
    }



}
