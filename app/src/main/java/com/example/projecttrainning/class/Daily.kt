package com.example.projecttrainning.`class`

class Daily
{
    var id: String = ""
        get() = field
        set(value) {
            field = value
        }

    var dailyid: String = ""
        get() = field
        set(value) {
            field = value
        }

    var studentemail: String = ""
        get() = field
        set(value) {
            field = value
        }

    var dailydate: String = ""
        get() = field
        set(value) {
            field = value
        }

    var dailyreport: String = ""
        get() = field
        set(value) {
            field = value
        }

    var dailyremark: String = ""
        get() = field
        set(value) {
            field = value
        }



    fun printInfo() {
        println("ID: $id")
        println("Daily ID: $dailyid")
        println("Daily Date: $dailydate")
        println("Daily Email: $studentemail")
        println("Daily Report: $dailyreport")
        println("Daily ReMark: $dailyremark")

    }
}