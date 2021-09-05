package com.sample.socket.datalib.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class KotlinJsonAdapterQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class MoshiConverterQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WebSocketUrlQualifier