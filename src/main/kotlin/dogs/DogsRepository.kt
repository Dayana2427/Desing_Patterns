package dogs

import kotlinx.serialization.json.Json
import observer.Observable
import observer.Observer
import java.io.File

class DogsRepository private constructor(): Observable<List<Dog>> {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("dogs.json")

    private val _observers = mutableListOf<Observer<List<Dog>>>()
    override val observers
        get() = _observers.toList()

    private val _dogs: MutableList<Dog> = loadAllDogs()

    private fun loadAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    override val currentValue: List<Dog>
        get() = _dogs.toList()

    override fun registerObserver(observer: Observer<List<Dog>>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<List<Dog>>) {
        _observers.remove(observer)
    }

    fun addOnDogsChangedListener(observer: Observer<List<Dog>>) {
        registerObserver(observer)
    }

    fun addDog(breed: String, name: String, weight: Double) {
        val id = currentValue.maxOf { it.id } +1
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


