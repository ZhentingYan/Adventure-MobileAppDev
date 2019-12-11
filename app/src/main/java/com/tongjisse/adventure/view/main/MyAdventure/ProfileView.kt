package com.tongjisse.adventure.view.main.MyAdventure

import java.sql.SQLException

interface ProfileView {
    fun showUpdateProfileSuccess()
    fun showUpdateProfileFailure(error:SQLException)
}