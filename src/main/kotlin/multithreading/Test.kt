package multithreading

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    print ("Ingrese un número entre 0 y 1_000_000_000: ")
    val number = readln().toInt()
    var win = false
    thread {
        var seconds = 1
        while (!win){
            println(seconds++)
            Thread.sleep(1000)
        }
    }
    thread {
        while (true) {
            val option = Random.nextInt(1_000_000_001)
            if (option == number) {
                println("!Ganadador! Tu número es: $option")
                win = true
                break
            }
        }
    }
}

/*thread {
    repeat(100_000) {
        print(" 0 ")
        Thread.sleep(1000)
    }
}
repeat(100_000){
    print(" * ")
    Thread.sleep(1000)
}*/