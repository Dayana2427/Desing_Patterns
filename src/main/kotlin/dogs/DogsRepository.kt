package dogs

import kotlinx.serialization.json.Json
import observer.Observer
import java.io.File

class DogsRepository private constructor() {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("dogs.json")

    private val observers = mutableListOf<Observer<List<Dog>>>()

    private val _dogs: MutableList<Dog> = loadAllDogs()
    val dogs
        get() = _dogs.toList()

    private fun loadAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    private fun notifyObservers() {
        for (observer in observers) {
            observer.onChanged(dogs)
        }
    }

    fun addOnDogsChangedListener(observer: Observer<List<Dog>>) {
        observers.add(observer)
        observer.onChanged(dogs)
    }

    fun addDog(breed: String, name: String, weight: Double) {
        val id = dogs.maxOf { it.id } +1
        val dog = Dog(id,breed, name, weight)
        _dogs.add(dog)
        notifyObservers()
    }

    fun deleteDog(id: Int) {
        _dogs.removeIf { it.id == id }
        notifyObservers()
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


