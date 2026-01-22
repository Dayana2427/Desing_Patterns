package users

import command.Command
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class UsersRepository private constructor() {

    init {
        println("El repositorio se esta creando...  ")
    }

    private val file = File("users.json")

    private val usersList: MutableList<User> = loadAllUsers()

    private val _users = MutableObservable(usersList.toList())
    val users: Observable<List<User>>
        get() = _users

    private val _oldestUser = MutableObservable(usersList.maxBy { it.age })
    val oldestUser: Observable<User>
        get() = _oldestUser

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    fun addUser(firstName: String, lastname: String, age: Int) {
        Thread.sleep(10_000)
        val id = usersList.maxOf { it.id } +1
        val user = User(id, age, firstName, lastname)
        usersList.add(user)
        _users.currentValue = usersList.toList()
        if (age > _oldestUser.currentValue.age) {
            _oldestUser.currentValue = user
        }
    }

    fun deleteUser(id: Int) {
        Thread.sleep(10_000)
        usersList.removeIf { it.id == id }
        _users.currentValue = usersList.toList()
        val newOldest = usersList.maxBy { it.age }
        if (newOldest != _oldestUser.currentValue) {
            _oldestUser.currentValue = newOldest
        }
    }

    fun saveChanges()  {
        val content = Json.encodeToString(usersList)
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