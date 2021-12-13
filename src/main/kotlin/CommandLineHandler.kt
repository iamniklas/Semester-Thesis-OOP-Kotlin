object CommandLineHandler {
    fun register(_bank: Bank) {
        println("Enter single account details in the following order and hit enter after each one")
        println("First Name - Last Name - PIN")
        val newAccount = Account().apply {
            firstName = readLine()!!
            lastName = readLine()!!
            pin = readLine()!!
        }

        _bank.register(newAccount)
    }
    fun login(_bank: Bank) {
        println("Enter your card identifier")
        val cardIdentifier = readLine()!!
        println("Enter your PIN")
        val pin = readLine()!!

        _bank.login(cardIdentifier, pin)
    }
    fun logout(_bank: Bank) {
        _bank.logout()
    }
    fun withdraw(_bank: Bank) {
        println("Enter the amount to withdraw")
        _bank.withdraw(readLine()!!.toFloat())
    }
    fun deposit(_bank: Bank) {
        println("Enter the amount to deposit")
        _bank.deposit(readLine()!!.toFloat())
    }
    fun transfer(_bank: Bank) {
        //if(!_bank.requireLogin()) { return }
        println("Enter the target account's identifier")
        val accountId = readLine()!!
        println("Enter the amount of money to transfer")
        val moneyToTransfer = readLine()!!.toFloat()
        _bank.transfer(accountId, moneyToTransfer)
    }
    fun info(_bank: Bank) { _bank.getAccountInfo() }
    fun help(_bank: Bank) {
        println("Available actions: login, logout, register, withdraw, deposit, info, help, quit")
    }
}