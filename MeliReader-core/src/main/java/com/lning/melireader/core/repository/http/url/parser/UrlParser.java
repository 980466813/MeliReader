package com.lning.melireader.core.repository.http.url.parser;

import com.lning.melireader.core.repository.http.url.UrlManager;

import okhttp3.HttpUrl;

/**
 * Created by Ning on 2019/2/4.
 */

public interface UrlParser {

    void init(UrlManager urlManager);

    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
