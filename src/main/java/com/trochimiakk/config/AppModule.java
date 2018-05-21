package com.trochimiakk.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.trochimiakk.controllers.MainController;
import com.trochimiakk.exceptions.FailedToLoadSettingsException;
import com.trochimiakk.flashcards.FlashcardsManager;
import com.trochimiakk.settings.SettingsManager;

import java.util.Properties;

public class AppModule extends AbstractModule {

    protected void configure(){
        Names.bindProperties(binder(), getProperties());
        bind(SettingsManager.class).toInstance(new SettingsManager());
        bind(FlashcardsManager.class).toProvider(FlashcardsManagerProvider.class).in(Singleton.class);
    }

    private Properties getProperties(){
        SettingsManager manager = new SettingsManager();
        Properties settings;
        try {
            settings = manager.loadProperties();
            return settings;
        } catch (FailedToLoadSettingsException e) {
            settings = manager.getDefaultProperties();
            return settings;
        }
    }

}
