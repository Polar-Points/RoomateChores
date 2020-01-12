package dang.marty.roomatechores.repository

/**
 *   Created by Marty Dang on 2020-01-11
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class Repo {
    fun getChoresSavedLocally(): List<String> {
        return listOf("Sweep up kitchen", "Wipe counter")
    }

    fun getChoresSavedRemotely(): List<String> {
        return listOf("Sweep up kitchen", "Wipe counter")
    }
}