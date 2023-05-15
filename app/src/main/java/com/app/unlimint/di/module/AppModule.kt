package com.app.unlimint.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.unlimint.BuildConfig
import com.app.unlimint.data.api.ApiHelper
import com.app.unlimint.data.api.ApiHelperImpl
import com.app.unlimint.data.api.ApiService
import com.app.unlimint.data.repository.UnlimintRepository
import com.app.unlimint.database.AppDatabase
import com.app.unlimint.database.JokesDao
import com.app.unlimint.ui.main.viewmodel.UnlimintViewModel
import com.app.unlimint.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }


    single { providesDatabase(get()) }
    single { provideDao(get()) }

    single {
        UnlimintRepository(get(), get())
    }

    viewModel {
        UnlimintViewModel(get(), get())
    }


}

fun providesDatabase(application: Application): AppDatabase =
    Room.databaseBuilder(application, AppDatabase::class.java, "userdatabase")
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: AppDatabase): JokesDao = db.jokesDao()


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
