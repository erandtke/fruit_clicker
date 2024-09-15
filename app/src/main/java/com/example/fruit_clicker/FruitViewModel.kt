package com.example.fruit_clicker

class FruitViewModel
{
    private val _fruitCounts = mutableMapOf<String,Int>();

    val fruitCounts: Map<String,Int>
        get() = _fruitCounts.toMap()

    fun incrementCount(fruitName: String)
    {
        _fruitCounts[fruitName] = (_fruitCounts[fruitName] ?: 0) +1;
    }
}