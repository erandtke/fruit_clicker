package com.example.fruit_clicker

data class Fruit(val name: String, var count: Int, var resourceId: Int, val ripeness: Ripeness) {
    enum class Ripeness {
        RIPE,
        UNRIPE
    }
}
