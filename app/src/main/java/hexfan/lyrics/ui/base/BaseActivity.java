package hexfan.lyrics.ui.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import hexfan.lyrics.di.MyComponents;
import hexfan.lyrics.ui.main.MainApplication;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Created by Pawel on 20.06.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    public static BaseActivity get(BaseFragment fragment){
        return (BaseActivity) fragment.getActivity();
    }



    public MyComponents getMyComponents() {
        return ((MainApplication) getApplication()).getMyComponents();
    }


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }
}
