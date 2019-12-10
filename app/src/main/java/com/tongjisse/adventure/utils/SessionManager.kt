package com.tongjisse.adventure.utils

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import com.tongjisse.adventure.view.views.Welcome.WelcomeActivity

/**
 * SessionManager Used for quick SharedPreferences Get and Set
 * @author ZhentingYan
 */

class SessionManager(private val context: Context?) {
    //SharedPreferencesUtils Instance
    var utils = SharedPreferencesUtils.getInstance(context)
    /**
     * A set of param members with getters and setters
     * @author ZhentingYan
     */
    var isLoggedIn: Boolean = false
        get() = utils.readBoolean(IS_LOGIN, false)
        set(value) {
            utils.writeBoolean(IS_LOGIN, value)
            field = value
        }
    var email: String = ""
        get() = utils.readString(EMAIL, "")
        set(value) {
            utils.writeString(EMAIL, value)
            field = value
        }
    var firstName: String = ""
        get() = utils.readString(FIRST_NAME, "")
        set(value) {
            utils.writeString(FIRST_NAME, value)
            field = value
        }
    var lastName: String = ""
        get() = utils.readString(LAST_NAME, "")
        set(value) {
            utils.writeString(LAST_NAME, value)
            field = value
        }
    var phoneNum: String = ""
        get() = utils.readString(PHONE_NUM, "")
        set(value) {
            utils.writeString(PHONE_NUM, value)
            field = value
        }
    var district: String = ""
        get() = utils.readString(DISTRICT_NAME, "")
        set(value) {
            utils.writeString(DISTRICT_NAME, value)
            field = value
        }
    var province: String = ""
        get() = utils.readString(PROVINCE_NAME, "")
        set(value) {
            utils.writeString(PROVINCE_NAME, value)
            field = value
        }
    var city: String = ""
        get() = utils.readString(CITY_NAME, "")
        set(value) {
            utils.writeString(CITY_NAME, value)
            field = value
        }
    var defaultAddress: String = ""
        get() = this.province + " " + this.city + " " + this.district

    var longitude: String = ""
        get() = utils.readString(LONGITUDE, "")
        set(value) {
            utils.writeString(LONGITUDE, value)
            field = value
        }
    var latitude: String = ""
        get() = utils.readString(LATITUDE, "")
        set(value) {
            utils.writeString(LATITUDE, value)
            field = value
        }
    var age: Int = 0
        get() = utils.readInteger(AGE, 0)
        set(value) {
            utils.writeInteger(AGE, value)
            field = value
        }
    var password: String = ""
        get() = utils.readString(PASSWORD, "")
        set(value) {
            utils.writeString(PASSWORD, value)
            field = value
        }

    /**
     * Call this Function when user successfully log in
     * @author ZhentingYan
     */
    fun createLoginSession(email: String, firstName: String, lastName: String, phoneNum: String,age:Int,password:String) {
        this.isLoggedIn = true
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNum = phoneNum
        this.age=age
        this.password=password
    }

    /**
     * Call this Function when location info has changed
     * @author ZhentingYan
     */
    fun refineLocation(province: String, city: String, district: String, longitude: String, latitude: String) {
        this.province = province
        this.city = city
        this.district = district
        this.longitude = longitude
        this.latitude = latitude
    }

    /**
     * Call this Function when user log out
     * @author ZhentingYan
     */
    fun logoutUser() {
        // Clearing all data from Shared Preferences
        utils.clearAll();
        // After logout redirect user to LogIn Activity
        val intent = Intent(context, WelcomeActivity::class.java)
        // Add new Flag to start new Activity
        // Closing all the Activities
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // Staring Login Activity
        context!!.startActivity(intent)
    }

    //Common used final Strings
    companion object {
        val EMAIL = "EMAIL"
        val FIRST_NAME = "FIRST_NAME"
        val LAST_NAME = "LAST_NAME"
        val PHONE_NUM = "PHONE_NUM"
        val DISTRICT_NAME = "DISTRICT_NAME"
        val IS_LOGIN = "IS_LOGIN"
        val PROVINCE_NAME = "PROVINCE_NAME"
        val CITY_NAME = "CITY_NAME"
        val LATITUDE = "LATITUDE"
        val LONGITUDE = "LONGITUDE"
        val PASSWORD="PASSWORD"
        val AGE="AGE"
    }
}
