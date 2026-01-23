package users

import command.Command

sealed interface AdministratorCommands: Command {

    data class AddUser(
        val repository: UsersRepository,
        val firstName: String,
        val lastName: String,
        val age: Int
    ) : AdministratorCommands {
        override fun excecute() {
            repository.addUser(firstName, lastName, age)
        }
    }

    data class DeleteUser(
        val repository: UsersRepository,
        val id: Int
    ) : AdministratorCommands {
        override fun excecute() {
            repository.deleteUser(id)
        }
    }

    data class SaveChanges(
        val repository: UsersRepository
    ) : AdministratorCommands {
        override fun excecute() {
            repository.saveChanges()
        }
    }
}