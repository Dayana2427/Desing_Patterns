package builder

fun main() {
    val drink = Drink.Builder()
    .type("Tea")
    .temperature("Cold")
    .diningOption("In cafe")
    .build()
     val drink2 = Drink(type = "Tea")
    println(drink)
}