class Account {
    var firstName: String = ""
    var lastName: String = ""

    var accountIdentifier: String = ""
    var pin: String = ""

    var accountBalance: Float = 0.0f
    var transactionHistory: MutableList<Transaction> = mutableListOf()

    override fun toString(): String {
        return "Account Owner: $firstName $lastName \nAccount Balance: $accountBalance€ \nTotal Transactions: ${transactionHistory.size}"
    }

    fun withdraw(_amount: Float) {
        if(_amount > accountBalance) {
            println("Not enough money stored in your account")
            println("Quitting...")
        }
        else {
            accountBalance -= _amount
            println("Withdrawn $_amount from your account")
            println("New Account Balance: $accountBalance")
        }
    }

    fun deposit(_amount: Float) {
        accountBalance += _amount
        println("Deposit $_amount to your account")
        println("New Account Balance: $accountBalance")
    }
}