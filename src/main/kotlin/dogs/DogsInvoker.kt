package dogs

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object DogsInvoker: Invoker {

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

    override fun addCommand(command: Command) {
        println("Nuevo comando: $command")
        commands.add(command)
    }
}