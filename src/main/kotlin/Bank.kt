import accounttypes.Account
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

        newAccount.addRegistrationAmount()

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

    fun withdraw(_amount: Float?, _transactionType: TransactionType) : Int {
        if(!requireLogin()) { return 1 }
        if(_amount == null) {
            return 2
        }
        loggedInAs!!.withdraw(_amount, _transactionType)
        return 0
    }

    fun deposit(_amount: Float?, _transactionType: TransactionType) : Int {
        if(!requireLogin()) { return 1 }
        if (_amount == null) {
            return 2
        }
        loggedInAs!!.deposit(_amount, _transactionType)
        return 0
    }

    fun transfer(_accountId: String, _moneyToTransfer: Float?) : Int{
        if(!requireLogin()) { return 1 }
        if(_moneyToTransfer == null) {
            return 2
        }
        if(_moneyToTransfer < 0.0f) {
            return 3
        }
        if(loggedInAs!!.accountBalance < _moneyToTransfer) {
            return 4
        }
        if(!registeredAccounts.any { it.accountIdentifier.contentEquals(_accountId) }) {
            return 5
        }

        //Better with built-in kotlin functions (e.g. first {it ->}), but this will only return a copy. That is why I'm not using it here
        for (i in 0 until registeredAccounts.size) {
            if(registeredAccounts[i].accountIdentifier == _accountId) {
                registeredAccounts[i].deposit(_moneyToTransfer, TransactionType.AccountTransfer)
                loggedInAs!!.withdraw(_moneyToTransfer, TransactionType.AccountTransfer)
            }
        }

        return 0
    }

    fun getAccountInfo() : String? {
        if(!requireLogin()) { return null }
        return loggedInAs.toString()
    }

    fun getTransferHistory() : String? {
        if(requireLogin()) return null
        return loggedInAs!!.getLastFiveTransactions()
    }

    fun requireLogin(): Boolean {
        return loggedInAs != null
    }

    override fun toString(): String {
        return Gson().toJson(registeredAccounts)
    }
}