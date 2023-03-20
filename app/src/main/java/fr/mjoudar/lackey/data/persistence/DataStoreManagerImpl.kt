package fr.mjoudar.lackey.data.persistence

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import fr.mjoudar.lackey.domain.models.Address
import fr.mjoudar.lackey.domain.models.User
import kotlinx.coroutines.flow.map

/***************************************************************************************************
 * DataStoreManagerImpl class - an implementation of DataStoreManager interface
 ***************************************************************************************************/

const val USER_DATASTORE = "USER_DATASTORE"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreManagerImpl(private val context: Context) : DataStoreManager {

    companion object {

        val FIRST_NAME = stringPreferencesKey("FIRST_NAME")
        val LAST_NAME = stringPreferencesKey("LAST_NAME")
        val STREET_CODE = stringPreferencesKey("STREET_CODE")
        val STREET = stringPreferencesKey("STREET")
        val CITY = stringPreferencesKey("CITY")
        val ZIPCODE = stringPreferencesKey("ZIPCODE")
        val COUNTRY = stringPreferencesKey("COUNTRY")
        val BIRTH_DATE = stringPreferencesKey("BIRTH_DATE")
    }

    override suspend fun updateUser(user: User) {
        context.dataStore.edit {

            it[FIRST_NAME] = user.firstName
            it[LAST_NAME] = user.lastName
            it[STREET_CODE] = user.address.streetCode
            it[STREET] = user.address.street
            it[CITY] = user.address.city
            it[ZIPCODE] = user.address.zipCode.toString()
            it[COUNTRY] = user.address.country
            it[BIRTH_DATE] = user.birthDate.toString()
        }
    }

    override fun retrieveUser() = context.dataStore.data.map {
        User(
            it[FIRST_NAME]?:"",
            it[LAST_NAME]?:"",
            Address(
                it[STREET_CODE]?:"",
                it[STREET]?:"",
                it[CITY]?:"",
                it[ZIPCODE]?.toInt()?:0,
                it[COUNTRY]?:"",
            ),
            it[BIRTH_DATE]?.toLong()?:0,
        )
    }
}