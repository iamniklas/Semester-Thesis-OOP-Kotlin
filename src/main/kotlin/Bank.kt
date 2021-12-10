import com.google.gson.Gson
import kotlin.concurrent.fixedRateTimer

class Bank {
    var loggedInAs: Account? = null
    var registeredAccounts: MutableList<Account> = mutableListOf()

    init {
        loggedInAs = Account()
        loggedInAs!!.apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            accountBalance = 1470.41f
        }
    }

    fun register() {
        var newAccount = Account()
        println("Enter account details in the following order")
        println("First Name - Last Name - PIN")
        newAccount.apply {
            firstName = readLine()!!
            lastName = readLine()!!
            pin = readLine()!!
        }

        loggedInAs = newAccount
        registeredAccounts.add(newAccount)
        println("Welcome ${newAccount.firstName} ${newAccount.lastName}!")
        println("You can now use our services")
    }

    fun login() {

    }

    fun logout() {
        if(!requireLogin()) { return }
        loggedInAs = null
        println("Successfully logged out")
    }

    fun withdraw() {
        if(!requireLogin()) { return }
        loggedInAs!!.withdraw(readLine()!!.toFloat())
    }

    fun deposit() {
        if(!requireLogin()) { return }
        loggedInAs!!.deposit(readLine()!!.toFloat())
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