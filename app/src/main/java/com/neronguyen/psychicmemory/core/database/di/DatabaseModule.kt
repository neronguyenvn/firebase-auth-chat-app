package com.neronguyen.psychicmemory.core.database.di

import com.neronguyen.psychicmemory.core.database.LocalDataSource
import com.neronguyen.psychicmemory.core.database.model.MessageEntity
import com.neronguyen.psychicmemory.core.database.model.SenderInfoEntity
import com.neronguyen.psychicmemory.core.database.realm.RealmDatabase
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
