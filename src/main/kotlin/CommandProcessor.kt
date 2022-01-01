object CommandProcessor {
    fun runCommand(_bank: Bank, _command: String) {
        when(_command.trim()) {
            "login" ->      { CommandLineHandler.login(_bank) }
            "logout" ->     { CommandLineHandler.logout(_bank) }
            "register" ->   { CommandLineHandler.register(_bank) }
            "withdraw" ->   { CommandLineHandler.withdraw(_bank) }
            "deposit" ->    { CommandLineHandler.deposit(_bank) }
            "transfer" ->   { CommandLineHandler.transfer(_bank) }
            "info" ->       { CommandLineHandler.info(_bank) }
            "history" ->    { CommandLineHandler.info(_bank) }
            "help" ->       { CommandLineHandler.help() }
            "quit" ->       { Program.running = false }
            else ->         { println("Unknown command, try again!") }
        }
    }
}