package users

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object UsersInvoker: Invoker<AdministratorCommands> {

    private val commands = LinkedBlockingQueue<Command>()

    init {
        thread {
            while (true) {
                println("Espere...")
                val command = commands.take()
                println("Ejecutando $command...")
                command.excecute()
                println("Ejecutado $command")
            }
        }
    }

    override fun addCommand(command: AdministratorCommands) {
        println("Nuevo comando: $command")
        commands.add(command)
    }
}