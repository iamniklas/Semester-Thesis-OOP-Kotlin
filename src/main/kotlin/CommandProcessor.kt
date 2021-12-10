object CommandProcessor {
    fun runCommand(_bank: Bank, _command: String) {
        when(_command) {
            "login" ->  { _bank.login() }
            "logout" ->  { _bank.logout() }
            "register" -> { _bank.register() }
            "withdraw" -> { _bank.withdraw() }
            "deposit" -> { _bank.deposit() }
            "admininfo" -> println(_bank.toString())
            "info" -> println(_bank.loggedInAs.toString())
            "help" -> println("Available actions: login, logout, register, withdraw, deposit, info, help, quit")
            "quit" -> Program.running = false

            else -> println("Unknown command, try again!")
        }
    }
}