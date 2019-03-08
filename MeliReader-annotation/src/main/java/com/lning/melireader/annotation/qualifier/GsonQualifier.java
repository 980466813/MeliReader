package com.lning.melireader.annotation.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Win on 2018/5/7.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GsonQualifier {
}
