import accounttypes.*

object CommandLineHandler {
    fun register(_bank: Bank) {
        println("Enter single account details in the following order and hit enter after each one")
        println("First Name - Last Name - PIN - AccountType (0: BasicAccount, 1: StandardAccount, 2: SuperPremiumAccount)")
        val firstNameLine = readLine()!!
        val lastNameLine = readLine()!!
        val pinLine = readLine()!!

        //Read AccountType
        when(readLine()!!.toInt()) {
            0 -> {
                val basicAccount = BasicAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                _bank.register(basicAccount)
            }
            1 -> {
                val standardAccount = StandardAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                _bank.register(standardAccount)
            }
            2 -> {
                val superPremiumAccount = SuperPremiumAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                _bank.register(superPremiumAccount)
            }
            else -> {
                println("Invalid input")
            }
        }
    }
    fun login(_bank: Bank) {
        println("Enter your account identifier")
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
        _bank.withdraw(readLine()!!.toFloat(), TransactionType.ATM)
    }
    fun deposit(_bank: Bank) {
        println("Enter the amount to deposit")
        _bank.deposit(readLine()!!.toFloat(), TransactionType.ATM)
    }
    fun transfer(_bank: Bank) {
        if(!_bank.requireLogin()) { return }
        println("Enter the target account's identifier")
        val accountId = readLine()!!
        println("Enter the amount of money you want to transfer")
        val moneyToTransfer = readLine()!!.toFloat()
        _bank.transfer(accountId, moneyToTransfer)
    }
    fun info(_bank: Bank) { _bank.getAccountInfo() }
    fun history(_bank: Bank) { println(_bank.getTransferHistory()) }
    fun help() {
        println("Available actions: login, logout, register, withdraw, deposit, info, history, help, quit")
    }
}