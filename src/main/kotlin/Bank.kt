import accounttypes.Account
import accounttypes.StandardAccount
import accounttypes.TransactionType
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Bank {
    var loggedInAs: Account? = null
    var registeredAccounts: MutableList<Account> = mutableListOf()

    companion object {
        const val leadingSequence = "DE69"
        const val blz = "71150000"
    }

    fun register(newAccount: Account) : Int {
        newAccount.accountIdentifier = leadingSequence+blz+(System.currentTimeMillis()/1000).toString()

        loggedInAs = newAccount
        loggedInAs?.lastTimeLoggedIn = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        registeredAccounts.add(newAccount)
        return 0
    }

    fun login(_cardIdentifier: String, _pin: String) : Int {
        val targetAccount = registeredAccounts.filter { it -> it.accountIdentifier == _cardIdentifier && it.pin == _pin }
        if(targetAccount.isEmpty()) {
            return 1
        }

        loggedInAs = targetAccount.first()
        loggedInAs?.lastTimeLoggedIn = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        return 0
    }

    fun logout() : Int {
        if(!requireLogin()) { return 1 }
        loggedInAs = null
        return 0
    }

    fun withdraw(_amount: Float, _transactionType: TransactionType) : Int {
        if(!requireLogin()) { return 1 }
        loggedInAs!!.withdraw(_amount, _transactionType)
        return 0
    }

    fun deposit(_amount: Float, _transactionType: TransactionType) : Int {
        if(!requireLogin()) { return 1 }
        loggedInAs!!.deposit(_amount, _transactionType)
        return 0
    }

    fun transfer(_accountId: String, _moneyToTransfer: Float) : Int{
        if(!requireLogin()) { return 1 }
        if(!registeredAccounts.any { it.accountIdentifier.contentEquals(_accountId) }) {
            return 2
        }
        if(loggedInAs!!.accountBalance < _moneyToTransfer) {
            return 3
        }

        val targetAccount = registeredAccounts.first { it -> it.accountIdentifier.contentEquals(_accountId) }

        targetAccount.deposit(_moneyToTransfer, TransactionType.AccountTransfer)
        loggedInAs!!.withdraw(_moneyToTransfer, TransactionType.AccountTransfer)

        return 0
    }

    fun getAccountInfo() : String {
        if(!requireLogin()) { return "null" }
        return loggedInAs.toString()
    }

    fun getTransferHistory() : String {
        if(requireLogin()) return "null"
        return loggedInAs!!.getLastFiveTransactions()
    }

    fun requireLogin(): Boolean {
        return loggedInAs != null
    }

    override fun toString(): String {
        return Gson().toJson(registeredAccounts)
    }
}