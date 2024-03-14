package com.example.europrofile.domain

class User (val email: String, val id: String) {
    constructor(id: String, name: String, number: String, email: String, password: String) : this(email, id)
}