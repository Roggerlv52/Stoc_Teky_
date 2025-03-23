package com.rogger.myapplication.ui.commun.base

import com.rogger.myapplication.ui.login.data.FakeDataSource
import com.rogger.myapplication.ui.login.data.LoginRepository
import com.rogger.myapplication.ui.register.data.FakeRegisterDataSource
import com.rogger.myapplication.ui.register.data.RegisterRepository
import com.rogger.myapplication.ui.splashScreen.data.FakeLocalDataSource
import com.rogger.myapplication.ui.splashScreen.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }

    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }


    fun registerEmailRepositoy() : RegisterRepository {
        return RegisterRepository(FakeRegisterDataSource())
    }
    /*
    fun searchRepository():SearchRepository{
        return SearchRepository(SearchFakeRemoteDataSource())
    }
    fun profileRepository():ProfileRepository{
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache,PostListMemoryCache))
    }
    fun homeRepository(): HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }
    fun addrepository() : AddRepository{
        return AddRepository(AddFakeRemoteDataSource(), AddLocalDataSource())
    }
    fun postRepository(context:Context):PostRepository{
        return PostRepository(PostLocalDataSource(context))
    }
     */
}