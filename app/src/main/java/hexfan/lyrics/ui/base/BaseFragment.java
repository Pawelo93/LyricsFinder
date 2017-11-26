package hexfan.lyrics.ui.base;


import android.app.Fragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Pawel on 20.06.2017.
 */

public abstract class BaseFragment extends Fragment implements LifecycleRegistryOwner {

    protected LifecycleRegistry lifecycleRegistry;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        lifecycleRegistry = new LifecycleRegistry(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    @Override
    public void onPause() {
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

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}

