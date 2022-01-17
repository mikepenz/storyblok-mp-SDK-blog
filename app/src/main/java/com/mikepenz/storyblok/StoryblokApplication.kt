package com.mikepenz.storyblok

import android.app.Application
import co.touchlab.kermit.Logger
import com.mikepenz.common.di.initKoin
import com.mikepenz.storyblok.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.Level

class StoryblokApplication : Application(), KoinComponent {
    private val logger: Logger by inject()

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.ERROR)
            androidContext(this@StoryblokApplication)
            modules(appModule)
        }

        logger.d { "StoryblokApplication" }
    }
}
