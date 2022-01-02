import accounttypes.*

object CommandLineHandler {
    fun register(_bank: Bank) {
        println("Enter single account details in the following order and hit enter after each one")
        println("First Name - Last Name - PIN - AccountType (0: BasicAccount, 1: StandardAccount, 2: SuperPremiumAccount)")
        val firstNameLine = readLine()!!
        val lastNameLine = readLine()!!
        val pinLine = readLine()!!

        var result = 0

        //Read AccountType
        when(readLine()!!.toInt()) {
            0 -> {
                val basicAccount = BasicAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                result = _bank.register(basicAccount)
            }
            1 -> {
                val standardAccount = StandardAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                result = _bank.register(standardAccount)
            }
            2 -> {
                val superPremiumAccount = SuperPremiumAccount().apply {
                    firstName = firstNameLine
                    lastName = lastNameLine
                    pin = pinLine
                }
                result = _bank.register(superPremiumAccount)
            }
            else -> {
                println("Invalid input")
            }
        }

        if (result == 0) {
            println("Welcome ${_bank.loggedInAs!!.firstName} ${_bank.loggedInAs!!.lastName}")
            println("Your card identifier: ${_bank.loggedInAs!!.accountIdentifier}")
            println("You can now use our services")
        } else {
            println("Registration failed, Error-Code: $result")
        }
    }

    fun login(_bank: Bank) {
        println("Enter your account identifier")
        val cardIdentifier = readLine()!!
        println("Enter your PIN")
        val pin = readLine()!!

        val result = _bank.login(cardIdentifier, pin)

        if (result == 0) {
            println("Welcome ${_bank.loggedInAs!!.firstName} ${_bank.loggedInAs!!.lastName}")
            println("Last time logged in: ${_bank.loggedInAs!!.lastTimeLoggedIn}")
        } else {
            println("Login failed, Error-Code: $result")
        }
    }

    fun logout(_bank: Bank) {
        val result = _bank.logout()

        if (result == 0) {
            println("Logout successful")
        } else {
            println("Logout failed, Error-Code: $result")
        }
    }

    fun withdraw(_bank: Bank) {
        println("Enter the amount to withdraw")
        val result = _bank.withdraw(readLine()!!.toFloat(), TransactionType.ATM)

        if (result == 0) {
            println("Withdraw successful")
        } else {
            println("Withdraw failed, Error-Code: $result")
        }
    }

    fun deposit(_bank: Bank) {
        println("Enter the amount to deposit")
        val result = _bank.deposit(readLine()!!.toFloat(), TransactionType.ATM)

        if (result == 0) {
            println("Deposit successful")
        } else {
            println("Deposit failed, Error-Code: $result")
        }
    }

    fun transfer(_bank: Bank) {
        if(!_bank.requireLogin()) { return }
        println("Enter the target account's identifier")
        val accountId = readLine()!!
        println("Enter the amount of money you want to transfer")
        val moneyToTransfer = readLine()!!.toFloat()
        val result = _bank.transfer(accountId, moneyToTransfer)

        if (result == 0) {
            println("Transfer successful")
        } else {
            println("Transfer failed, Error-Code: $result")
        }
    }
    fun info(_bank: Bank) {
        val result = _bank.getAccountInfo()

        if (result != "null") {
            println(result)
        } else {
            println("Deposit failed! Account info empty, are you logged in?")
        }
    }

    fun history(_bank: Bank) {
        val result = _bank.getTransferHistory()

        if (result != "null") {
            println(result)
        } else {
            println("Fetching History failed! History invalid, are you logged in?")
        }
    }

    fun help() {
        println("Available actions: login, logout, register, withdraw, deposit, info, history, help, quit")
    }
}