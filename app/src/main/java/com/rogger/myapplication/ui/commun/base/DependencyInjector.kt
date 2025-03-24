package com.rogger.myapplication.ui.commun.base

import android.content.Context
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.data.LoginFireDataSouce
import com.rogger.myapplication.ui.login.data.LoginRepository
import com.rogger.myapplication.ui.login.presentation.LoginPresenter
import com.rogger.myapplication.ui.register.data.FireRegisterDataSource
import com.rogger.myapplication.ui.register.data.RegisterRepository
import com.rogger.myapplication.ui.splashScreen.data.SplashLocalDataSource
import com.rogger.myapplication.ui.splashScreen.data.SplashRepository

object DependencyInjector {

    fun splashRepository(context: Context) : SplashRepository {
        return SplashRepository(SplashLocalDataSource(context))
    }

    fun loginRepository() : LoginRepository {
        return LoginRepository(LoginFireDataSouce())
    }
    fun loginPresenter(view: Login.View?, context: Context): Login.Presenter { // Adiciona o método loginPresenter
        val splashLocalDataSource = SplashLocalDataSource(context)
        return LoginPresenter(view, loginRepository(), splashLocalDataSource)
    }


    fun registerEmailRepositoy() : RegisterRepository {
        return RegisterRepository(FireRegisterDataSource())
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