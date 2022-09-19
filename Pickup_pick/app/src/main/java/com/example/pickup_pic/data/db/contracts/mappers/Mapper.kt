package com.example.pickup_pic.data.db.contracts.mappers

//в UI слое нельзя с entity работать, поэтому мы создаем преобразаватель, чтобы можно было работать
interface Mapper<in I, out O> {
    fun map(from: I): O
}