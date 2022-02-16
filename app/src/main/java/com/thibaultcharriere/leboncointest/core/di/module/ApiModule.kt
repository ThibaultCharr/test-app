package com.thibaultcharriere.leboncointest.core.di

import com.squareup.moshi.Moshi
import com.thibaultcharriere.leboncointest.BuildConfig
import com.thibaultcharriere.leboncointest.data.remote.api.AlbumApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {

    single { provideHttpLoggingInterceptor() }
    single { provideAuthInterceptorOkHttpClient(get()) }
    single { provideMoshi() }
    single { provideUserService(get(), get()) }
}

private fun provideUserService(moshi: Moshi, okHttpClient: OkHttpClient): AlbumApi =
    Retrofit.Builder()
        .baseUrl("https://static.leboncoin.fr/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(AlbumApi::class.java)

private fun provideMoshi(): Moshi = Moshi.Builder().build()

private fun provideAuthInterceptorOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(httpLoggingInterceptor)
    .build()


private fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}