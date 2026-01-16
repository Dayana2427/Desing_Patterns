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
                    repository.saveChanges()
                    break
                }

                Operation.ADD_DOG -> addUser()
                Operation.DELETE_DOG -> deleteUser()
            }
        }
    }

    private fun deleteUser() {
        print("Ingrese ID: ")
        val id = readln().toInt()
        repository.deleteDog(id)
    }

    private fun addUser() {
        print("Ingrese la raz: ")
        val breed = readln()
        print("Ingrese el nombre: ")
        val name = readln()
        print("Ingrese el peso: ")
        val weight = readln().toDouble()
        repository.addDog(breed, name, weight)
    }
}




