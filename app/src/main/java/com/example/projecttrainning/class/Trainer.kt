package com.example.projecttrainning.`class`

class Trainer
{
    var id: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainerid: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainercompany: String = ""
        get() = field
        set(value) {
            field = value
        }

    var traineraddress: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainertype: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainerservice: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainercapasity: String = ""
        get() = field
        set(value) {
            field = value
        }

    var trainertel: String = ""
        get() = field
        set(value) {
            field = value
        }




    fun printInfo() {
        println("ID: $id")
        println("Trainer ID: $trainerid")
        println("Trainer Company: $trainercompany")
        println("Trainer Address: $traineraddress")
        println("Trainer Type: $trainertype")
        println("Trainer Service: $trainerservice")
        println("Trainer Cap: $trainercapasity")
        println("Trainer Tel: $trainertel")

    }
}