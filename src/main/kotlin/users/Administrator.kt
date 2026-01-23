package users

class Administrator {

    private val repository = UsersRepository.getInstance("qwerty")

    fun work() {
        while (true) {
            print("Ingrese una operación: ")
            val operations = Operation.entries
            for ((index, operation) in operations.withIndex()) {
                print("$index - ${operation.title}")
                if (index == operations.lastIndex) {
                    print(": ")
                } else {
                    print(", ")
                }
            }
            val operationIndex = readln().toInt()
            val operation = operations[operationIndex]
            when(operation) {
                Operation.EXIT -> {
                    UsersInvoker.addCommand(AdministratorCommands.SaveChanges(repository))
                    break
                }

                Operation.ADD_USER -> addUser()
                Operation.DELETE_USER -> deleteUser()
            }
        }
    }

    private fun deleteUser() {
        print("Ingrese ID: ")
        val id = readln().toInt()
        UsersInvoker.addCommand(AdministratorCommands.DeleteUser(repository, id))
    }

    private fun addUser() {
        print("Ingrese el nombre: ")
        val firstName = readln()
        print("Ingrese el apellido: ")
        val lastName = readln()
        print("Ingrese la edad: ")
        val age = readln().toInt()
        UsersInvoker.addCommand(AdministratorCommands.AddUser(repository, firstName, lastName, age))
    }
}




