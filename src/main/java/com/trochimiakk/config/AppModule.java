package com.trochimiakk.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.trochimiakk.exceptions.FailedToLoadSettingsException;
import com.trochimiakk.flashcards.FlashcardsManager;
import com.trochimiakk.settings.SettingsManager;

import java.util.Properties;

public class AppModule extends AbstractModule {

    protected void configure(){
        Names.bindProperties(binder(), getProperties());
        bind(String.class).annotatedWith(Names.named("settings file location")).toInstance(System.getProperty("user.home") + "\\.Flashcards\\settings\\settings.properties");
        bind(SettingsManager.class);
        bind(FlashcardsManager.class).toProvider(FlashcardsManagerProvider.class).in(Singleton.class);
    }

    private Properties getProperties(){
        SettingsManager manager = new SettingsManager(System.getProperty("user.home") + "\\.Flashcards\\settings\\settings.properties");
        Properties settings;
        try {
            settings = manager.loadSettings();
            return settings;
        } catch (FailedToLoadSettingsException e) {
            settings = manager.getDefaultSettings();
            return settings;
        }
    }

}
