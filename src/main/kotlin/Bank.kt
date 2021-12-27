import accounttypes.StandardAccount
import com.google.gson.Gson
import java.time.LocalDateTime

class Bank {
    var loggedInAs: StandardAccount? = null
    var registeredAccounts: MutableList<StandardAccount> = mutableListOf()

    companion object {
        const val leadingSequence = "DE69"
        const val blz = "71150000"
    }

    fun register(newAccount: StandardAccount) {
        newAccount.accountIdentifier =
            leadingSequence+blz+(System.currentTimeMillis()/1000).toString()

        loggedInAs = newAccount
        registeredAccounts.add(newAccount)
        println("Welcome ${newAccount.firstName} ${newAccount.lastName}!")
        println("Your card identifier: ${newAccount.accountIdentifier}")
        println("You can now use our services")
    }

    fun login(_cardIdentifier: String, _pin: String) {
        val targetAccount = registeredAccounts.filter { it -> it.accountIdentifier == _cardIdentifier && it.pin == _pin }
        if(targetAccount.isEmpty()) {
            println("Invalid credentials. No account has been found")
            return
        }

        loggedInAs = targetAccount.first()
        loggedInAs!!.lastTimeLoggedIn = LocalDateTime.now()
        println("Welcome ${loggedInAs!!.firstName} ${loggedInAs!!.lastName}")
    }

    fun logout() {
        if(!requireLogin()) { return }
        loggedInAs = null
        println("Successfully logged out")
    }

    fun withdraw(_amount: Float) {
        if(!requireLogin()) { return }
        loggedInAs!!.withdraw(_amount)
    }

    fun deposit(_amount: Float) {
        if(!requireLogin()) { return }
        loggedInAs!!.deposit(_amount)
    }

    fun transfer(_accountId: String, _moneyToTransfer: Float) {
        if(!requireLogin()) { return }
        if(!registeredAccounts.any { it.accountIdentifier.contentEquals(_accountId) }) {
            println("No account found with identifier $_accountId")
            return
        }
        if(loggedInAs!!.accountBalance < _moneyToTransfer) {
            println("You don't have enough money to transfer this amount")
        }

        val targetAccount = registeredAccounts.first { it -> it.accountIdentifier.contentEquals(_accountId) }
        targetAccount.accountBalance += _moneyToTransfer

        val transactionFeeOrCashback = _moneyToTransfer * (1 - loggedInAs!!.transactionMultiplier)
        loggedInAs!!.accountBalance -= _moneyToTransfer + transactionFeeOrCashback
        println("Successfully transferred money to ${targetAccount.firstName} ${targetAccount.lastName}")

        if(transactionFeeOrCashback > 0.0f) {
            println("A transaction fee of $transactionFeeOrCashback has been withdrawn from your account.")
        }
        else if(transactionFeeOrCashback < 0.0f) {
            println("You have been awarded with a cashback of $transactionFeeOrCashback")
        }
    }

    fun getAccountInfo() {
        if(!requireLogin()) { return }
        println(loggedInAs.toString())
    }

    private fun requireLogin(): Boolean {
        if(loggedInAs == null) {
            println("You have to be logged in to perform this action.")
            println("Login to your account and try again")
            return false
        }

        return true
    }

    override fun toString(): String {
        return Gson().toJson(registeredAccounts)
    }
}