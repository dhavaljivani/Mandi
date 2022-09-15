package com.jiva.mandi.di.component;


import com.jiva.mandi.di.module.FragmentModule;
import com.jiva.mandi.di.scope.FragmentScope;

import dagger.Component;

/*
 * Author: rotbolt
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
}
