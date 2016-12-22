package com.example.jon.fangnews.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by jon on 2016/12/19.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}
