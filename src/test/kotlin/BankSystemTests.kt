import accounttypes.BasicAccount
import accounttypes.StandardAccount
import accounttypes.SuperPremiumAccount
import accounttypes.TransactionType
import org.junit.jupiter.api.Test
import java.io.FileReader
import kotlin.math.floor

class BankSystemTests {

    var bank: Bank = Bank()

    @Test
    fun testRegisterAccounts() {
        val basicAccount = BasicAccount().apply {
            firstName = "Basic Account"
        }
        val standardAccount = StandardAccount().apply {
            firstName = "Standard Account"
        }
        val superPremiumAccount = SuperPremiumAccount().apply {
            firstName = "Super Premium Account"
        }

        bank.register(basicAccount)
        println(bank.loggedInAs!!.firstName)
        bank.register(standardAccount)
        println(bank.loggedInAs!!.firstName)
        bank.register(superPremiumAccount)
        println(bank.loggedInAs!!.firstName)
    }

    @Test
    fun testAccountInfo() {
        bank.register(SuperPremiumAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "0000"
        })

        val info = bank.getAccountInfo()

        val expectedOutput = "Account Owner: Niklas Englmeier \nCard Identifier: ${bank.loggedInAs!!.accountIdentifier} \nAccount Balance: 100.0 \nPIN: 0000"
        if(info != expectedOutput) {
            error("Output invalid! \nExpected \n$expectedOutput\n, actual \n$info")
        }
    }

    @Test
    fun testLogin() {
        val account = BasicAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)
        val cardId = bank.loggedInAs!!.accountIdentifier

        bank.logout()

        if(bank.login(cardId, "1235") == 0 && !bank.requireLogin()) {
            error("This should not have happened")
        }
        if(bank.login(cardId, "1234") != 0 && bank.requireLogin()) {
            error("This should not have happened")
        }
    }

    @Test
    fun testRegistration() {
        val account = BasicAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)

        if(bank.registeredAccounts.size != 1) {
            error("Invalid Account Registry Size")
        }
    }

    @Test
    fun testWithdrawAndDeposit() {
        val account = StandardAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)

        if(bank.deposit(50.0f, TransactionType.ATM) != 0 || bank.loggedInAs!!.accountBalance != 100.0f) {
            error("Deposit fail")
        }
        if(bank.withdraw(50.0f, TransactionType.ATM) != 0 || bank.loggedInAs!!.accountBalance != 50.0f) {
            error("Withdraw fail")
        }
    }

    @Test
    fun testTransferDepositAndWithdraw() {
        var cardId1 = ""
        val cardPin1 = "0000"
        var cardId2 = ""
        val cardPin2 = "1111"

        val account1 = StandardAccount().apply {
            firstName = "Dominik"
            lastName = "Meister"
            pin = cardPin1
        }

        bank.register(account1)
        cardId1 = bank.loggedInAs!!.accountIdentifier
        println(bank.getAccountInfo())
        println("---")

        println("LOGOUT")
        bank.logout()
        println("---")

        val account2 = StandardAccount().apply {
            firstName = "Lucas"
            lastName = "Wirth"
            pin = cardPin2
        }

        Thread.sleep(2000)

        bank.register(account2)
        cardId2 = bank.loggedInAs!!.accountIdentifier
        println(bank.getAccountInfo())
        println("---")

        bank.transfer(cardId1, 50.0f)

        if(bank.loggedInAs!!.accountBalance != 0.0f) {
            error("Transfer receive error. Expected 0.0, actual ${bank.loggedInAs!!.accountBalance}")
        }

        //TODO Test deposit & withdraw here

        bank.logout()
        bank.login(cardId1, cardPin1)
        println("---")
        println("Account 1 Balance")
        if(bank.loggedInAs!!.accountBalance != 100.0f) {
            error("Transfer receive error. Expected 100.0, actual ${bank.loggedInAs!!.accountBalance}")
        }
    }
}