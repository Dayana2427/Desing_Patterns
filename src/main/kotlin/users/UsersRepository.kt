package users

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import observer.MutableObservable
import java.io.File

class UsersRepository private constructor() {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("users.json")

    private val _users: MutableList<User> = loadAllUsers()

    val users = MutableObservable(_users.toList())

    val oldestUser = MutableObservable(_users.maxBy { it.age })

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    fun addUser(firstName: String, lastname: String, age: Int) {
        val id = _users.maxOf { it.id } +1
        val user = User(id, age, firstName, lastname)
        _users.add(user)
        users.currentValue = _users.toList()
        if (age > oldestUser.currentValue.age) {
            oldestUser.currentValue = user
        }
    }

    fun deleteUser(id: Int) {
        _users.removeIf { it.id == id }
        users.currentValue = _users.toList()
        val newOldest = _users.maxBy { it.age }
        if (newOldest != oldestUser.currentValue) {
            oldestUser.currentValue = newOldest
        }
    }

    fun saveChanges()  {
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