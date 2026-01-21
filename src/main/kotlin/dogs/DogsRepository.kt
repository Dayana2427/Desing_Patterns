package dogs

import kotlinx.serialization.json.Json
import observer.MutableObservable
import java.io.File

class DogsRepository private constructor() {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("dogs.json")

    private val _dogs: MutableList<Dog> = loadAllDogs()

    val dogs = MutableObservable(_dogs.toList())

    private fun loadAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    fun addDog(breed: String, name: String, weight: Double) {
        val id = _dogs.maxOf { it.id } +1
        val dog = Dog(id,breed, name, weight)
        _dogs.add(dog)
        dogs.currentValue = _dogs.toList()
    }

    fun deleteDog(id: Int) {
        _dogs.removeIf { it.id == id }
        dogs.currentValue = _dogs.toList()
    }

    fun saveChanges() {
        val content = Json.encodeToString(_dogs)
        file.writeText(content)
    }

    companion object {

        private val lock = Any()
        private var instance: DogsRepository? = null

        fun getInstance(password: String): DogsRepository {
            val correctPassword = File("password_dogs.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }

                return DogsRepository().also {
                    instance = it
                }
            }
        }
    }
}