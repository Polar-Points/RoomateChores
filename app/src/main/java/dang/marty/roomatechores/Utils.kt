package dang.marty.roomatechores

/**
 *   Created by Marty Dang on 2020-03-10
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
object Utils {

    fun generateGroupCode(): String {
        val values = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz|!£$%&/=@#"
        var code = ""
        for(x in 0..9){
            code += values[(0..values.length).random()]
        }
        return code
    }
}