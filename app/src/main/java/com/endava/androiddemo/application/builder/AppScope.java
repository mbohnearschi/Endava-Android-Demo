package com.endava.androiddemo.application.builder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
@interface AppScope {
}