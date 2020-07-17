package com.abhishek.architecturepattern_livedata_pagging_di.di;


import com.abhishek.architecturepattern_livedata_pagging_di.ui.PagingDemoAct;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(PagingDemoAct pagingDemoAct);

}
