package com.neronguyenvn.chattoggle.core.database.di

import com.neronguyenvn.chattoggle.core.database.LocalDataSource
import com.neronguyenvn.chattoggle.core.database.model.MessageEntity
import com.neronguyenvn.chattoggle.core.database.model.SenderInfoEntity
import com.neronguyenvn.chattoggle.core.database.realm.RealmDatabase
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val databaseModule = module {
    single {
        Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    SenderInfoEntity::class,
                    MessageEntity::class
                )
            )
        )
    }
    single<LocalDataSource> { RealmDatabase(get()) }
}
