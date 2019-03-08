package com.lning.melireader.annotation.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Win on 2018/4/23.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScope {
}
