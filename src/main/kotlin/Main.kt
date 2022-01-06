import Program.running
import accounttypes.Account
import accounttypes.BasicAccount

fun main(args: Array<String>) {
    val bank = Bank()

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