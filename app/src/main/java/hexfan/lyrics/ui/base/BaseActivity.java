package hexfan.lyrics.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import hexfan.lyrics.R;
import hexfan.lyrics.ui.main.MainApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Created by Pawel on 20.06.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    protected LifecycleRegistry lifecycleRegistry;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static BaseActivity get(BaseFragment fragment){
        return (BaseActivity) fragment.getActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        lifecycleRegistry = new LifecycleRegistry(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        unbind();
        super.onPause();
    }

    public void addSubscribe(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public abstract void bind();

    public void unbind(){
        compositeDisposable.clear();
    }


    protected void addFragment(BaseFragment baseFragment, @IdRes int container){
        getFragmentManager()
                .beginTransaction()
                .replace(container, baseFragment)
                .commit();
    }

    protected void addFragment(BaseFragment baseFragment, @IdRes int container, String backStack){
        getFragmentManager()
                .beginTransaction()
                .replace(container, baseFragment)
                .addToBackStack(backStack)
                .commit();
    }

//    protected void addFragmentBackstack(BaseFragment baseFragment){
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmentContainer, baseFragment)
//                .addToBackStack("baseFragment")
//                .commit();
//    }

//    /**
//     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
//     * performed by the {@code fragmentManager}.
//     *
//     */
//    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
//                                              @NonNull Fragment fragment, int frameId) {
//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(frameId, fragment);
//        transaction.commit();
//    }
//
//    /**
//     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
//     * performed by the {@code fragmentManager}.
//     *
//     */
//    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
//                                              @NonNull Fragment fragment, String tag) {
//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(fragment, tag);
//        transaction.commit();
//    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
