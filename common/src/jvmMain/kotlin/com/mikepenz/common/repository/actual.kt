package com.mikepenz.common.repository

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Logger(StaticConfig(logWriterList = listOf(CommonWriter()))) }
}
