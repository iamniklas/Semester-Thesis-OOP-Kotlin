import Program.running
import kotlin.math.log

fun main(args: Array<String>) {
    var bank = Bank()

    println("CMD BANK")
    println("BUILT WITH KOTLIN & RUN BY JVM")
    println("Type in 'help' if you need support")
    println("---------------")
    while(running) {
        CommandProcessor.runCommand(bank, readLine()!!)
    }
}

object Program {
    var running = true
}