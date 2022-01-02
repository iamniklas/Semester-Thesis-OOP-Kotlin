package accounttypes

import java.lang.Integer.min
import java.time.LocalDateTime
import kotlin.math.max

abstract class Account() {
    var firstName: String = ""
    var lastName: String = ""

    var accountIdentifier: String = ""
    var pin: String = ""

    lateinit var lastTimeLoggedIn: LocalDateTime

    var accountBalance: Float = 0.0f
    var transactionHistory: MutableList<Transaction> = mutableListOf()

    open fun withdraw(_amount: Float, transactionType: TransactionType) : Int {
        //Not enough money stored
        if(_amount > accountBalance) {
            return 1
        }

        //Invalid value
        if(_amount < 0) {
            return 2
        }

        accountBalance -= _amount
        addTransaction(transactionType, -_amount)
        return 0
    }

    open fun deposit(_amount: Float, transactionType: TransactionType) : Int {
        //Invalid value
        if(_amount < 0) {
            return 1
        }

        accountBalance += _amount
        addTransaction(transactionType, _amount)
        return 0
    }

    private fun addTransaction(transactionType: TransactionType, amount: Float) {
        transactionHistory.add(Transaction(transactionType, amount))
    }

    fun getLastFiveTransactions() : String {
        var output = ""
        val reverseTransactions = transactionHistory.reversed()

        for(i in 0 until min(reverseTransactions.size, 5)) {
            output += "$i: ${reverseTransactions[i].amount} ${reverseTransactions[i].type}\n"
        }
        return output
    }

    override fun toString(): String {
        return "Account Owner: $firstName $lastName \nCard Identifier: $accountIdentifier \nAccount Balance: $accountBalance€ \nTotal Transactions: ${transactionHistory.size}"
    }
}