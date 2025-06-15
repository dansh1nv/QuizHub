package ru.dansh1nv.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream
import androidx.datastore.core.Serializer as DataStoreSerializer

@Serializable
data class AppPreferences(
    val currentCity: String = "",
) {
    class Serializer(private val json: Json) : DataStoreSerializer<AppPreferences> {
        override val defaultValue: AppPreferences = AppPreferences()

        @OptIn(ExperimentalSerializationApi::class)
        override suspend fun readFrom(input: InputStream): AppPreferences {
            return json.decodeFromStream(serializer(), input)
        }

        @OptIn(ExperimentalSerializationApi::class)
        override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
            json.encodeToStream(serializer(), t, output)
        }

    }
}

class AppDataStore(private val delegate: DataStore<AppPreferences>) {
    val dataFlow: Flow<AppPreferences> = delegate.data

    suspend fun update(transform: AppPreferences.() -> AppPreferences) {
        delegate.updateData { prefs ->
            prefs.transform()
        }
    }
}