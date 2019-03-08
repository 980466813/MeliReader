package com.lning.melireader.app.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.lning.melireader.annotation.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

@Module
public class FragmentModule {

    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @FragmentScope
    RxPermissions provideRxPermissions() {
        return new RxPermissions(mFragment.getActivity());
    }

    @Provides
    @FragmentScope
    Items provideItems() {
        return new Items();
    }

    @Provides
    @FragmentScope
    MultiTypeAdapter provideMultiTypeAdapter(@FragmentScope Items items) {
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        return adapter;
    }
}
