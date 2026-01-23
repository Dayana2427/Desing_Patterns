package dogs

class Administrator {

    private val repository = DogsRepository.getInstance("sango")

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
                    DogsInvoker.addCommand(AdministratorCommands.SaveChanges(repository))
                    break
                }

                Operation.ADD_DOG -> addDog()
                Operation.DELETE_DOG -> deleteDog()
            }
        }
    }

    private fun deleteDog() {
        print("Ingrese ID: ")
        val id = readln().toInt()
        DogsInvoker.addCommand(AdministratorCommands.DeleteDog(repository, id))
    }

    private fun addDog() {
        print("Ingrese la raz: ")
        val breed = readln()
        print("Ingrese el nombre: ")
        val name = readln()
        print("Ingrese el peso: ")
        val weight = readln().toDouble()
        DogsInvoker.addCommand (AdministratorCommands.AddDog(repository, breed, name, weight))
    }
}




