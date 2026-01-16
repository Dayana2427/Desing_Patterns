package users

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import observer.Observer
import java.io.File

class UsersRepository private constructor() {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("users.json")

    private val observers = mutableListOf<Observer<List<User>>>()

    private val _users: MutableList<User> = loadAllUsers()
    val users
        get() = _users.toList()

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    private fun notifyObservers() {
        for (observer in observers) {
            observer.onChanged(users)
        }
    }

    fun registerObserver(observer: Observer<List<User>>) {
        observers.add(observer)
        observer.onChanged(users)
    }

    fun addUser(firstName: String, lastname: String, age: Int) {
        val id = users.maxOf { it.id } +1
        val user = User(id, age, firstName, lastname)
        _users.add(user)
        notifyObservers()
    }

    fun deleteUser(id: Int) {
        _users.removeIf { it.id == id }
        notifyObservers()
    }

    fun saveChanges() {
        val content = Json.encodeToString(_users)
        file.writeText(content)
    }

    companion object {

        private val lock = Any()
        private var instance: UsersRepository? = null

        fun getInstance(password: String): UsersRepository {
            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }

                return UsersRepository().also {
                    instance = it
                }
            }
        }
    }
}