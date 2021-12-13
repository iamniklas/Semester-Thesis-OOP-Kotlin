import java.time.LocalDateTime

class Account {
    var firstName: String = ""
    var lastName: String = ""

    var accountIdentifier: String = ""
    var pin: String = ""

    lateinit var lastTimeLoggedIn: LocalDateTime

    var accountBalance: Float = 0.0f
    var transactionHistory: MutableList<Transaction> = mutableListOf()

    override fun toString(): String {
        return "Account Owner: $firstName $lastName \nCard Identifier: $accountIdentifier \nAccount Balance: $accountBalance€ \nTotal Transactions: ${transactionHistory.size}"
    }

    fun withdraw(_amount: Float) {
        if(_amount > accountBalance) {
            println("Not enough money stored in your account")
            println("Quitting...")
            return
        }

        if(_amount < 0) {
            println("Withdraw value has to be greater than 0")
            return
        }

        accountBalance -= _amount
        println("Withdrawn $_amount from your account")
        println("New Account Balance: $accountBalance")
    }

    fun deposit(_amount: Float) {
        if(_amount < 0) {
            println("Deposit value has to be greater than 0")
            return
        }

        accountBalance += _amount
        println("Deposit $_amount to your account")
        println("New Account Balance: $accountBalance")
    }
}