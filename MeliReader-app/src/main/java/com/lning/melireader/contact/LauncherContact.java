package com.lning.melireader.contact;

/**
 * Created by Ning on 2019/2/6.
 */

public interface LauncherContact {
    interface Presenter<V extends View> {
        void skipToIndex();

        void getDefaultChannels();
    }

    interface View {
        void goToIndexPage(String json);
    }
}
