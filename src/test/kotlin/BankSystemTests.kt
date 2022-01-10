import accounttypes.BasicAccount
import accounttypes.StandardAccount
import accounttypes.SuperPremiumAccount
import accounttypes.TransactionType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BankSystemTests {
    @Test
    fun testRegisterAccounts() {
        val bank: Bank = Bank()
        val basicAccount = BasicAccount().apply {
            firstName = "Basic Account"
        }
        val standardAccount = StandardAccount().apply {
            firstName = "Standard Account"
        }
        val superPremiumAccount = SuperPremiumAccount().apply {
            firstName = "Super Premium Account"
        }

        assertEquals(0, bank.register(basicAccount), "Basic Account Registration failed")
        assertEquals(0, bank.register(standardAccount), "Standard Account Registration failed")
        assertEquals(0, bank.register(superPremiumAccount), "Super Premium Account Registration failed")

        println(bank.loggedInAs!!.firstName)
        bank.register(standardAccount)
        println(bank.loggedInAs!!.firstName)
        bank.register(superPremiumAccount)
        println(bank.loggedInAs!!.firstName)
    }

    @Test
    fun testAccountInfo() {
        val bank: Bank = Bank()
        bank.register(SuperPremiumAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "0000"
        })

        val info = bank.getAccountInfo()

        val expectedOutput = "Account Owner: Niklas Englmeier \nCard Identifier: ${bank.loggedInAs!!.accountIdentifier} \nAccount Balance: 100.0 \nPIN: 0000"

        assertNotEquals(null, info, "Account Info empty!")
        if(info != expectedOutput) {
            error("Output invalid! \nExpected \n$expectedOutput\n, actual \n$info")
        }
    }

    @Test
    fun testLogin() {
        val bank: Bank = Bank()
        val account = BasicAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)
        val cardId = bank.loggedInAs!!.accountIdentifier

        bank.logout()

        assertEquals(false, bank.login(cardId, "1235") == 0 && !bank.requireLogin(), "Login successful, but shouldn't be")
        assertEquals(true, bank.login(cardId, "1234") == 0 && bank.requireLogin(), "Login not successful, but should be")
    }

    @Test
    fun testRegistration() {
        val bank: Bank = Bank()
        val account = BasicAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)

        assertEquals(1, bank.registeredAccounts.size, "Invalid Account Registry Size")
    }

    @Test
    fun testWithdrawAndDeposit() {
        val bank: Bank = Bank()
        val account = StandardAccount().apply {
            firstName = "Niklas"
            lastName = "Englmeier"
            pin = "1234"
        }

        bank.register(account)

        assertEquals(true, bank.deposit(50.0f, TransactionType.ATM) == 0 && bank.loggedInAs!!.accountBalance == 100.0f, "Deposit fail")
        assertEquals(true, bank.withdraw(50.0f, TransactionType.ATM) == 0 || bank.loggedInAs!!.accountBalance == 50.0f, "Withdraw fail")
    }

    @Test
    fun testMoneyTransfer() {
        val bank: Bank = Bank()
        var cardId1 = ""
        val cardPin1 = "0000"
        var cardId2 = ""
        val cardPin2 = "1111"

        val account1 = StandardAccount().apply {
            firstName = "Dominik"
            lastName = "Meister"
            pin = cardPin1
        }

        assertEquals(0, bank.register(account1), "Account 1 Registration Failed")
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

        assertEquals(0, bank.register(account2), "Account 2 Registration Failed")
        cardId2 = bank.loggedInAs!!.accountIdentifier
        println(bank.getAccountInfo())
        println("---")

        assertEquals(0, bank.transfer(cardId1, 50.0f), "Transfer failed")

        if(bank.loggedInAs!!.accountBalance != 0.0f) {
            error("Transfer receive error. Expected 0.0, actual ${bank.loggedInAs!!.accountBalance}")
        }

        bank.logout()
        bank.login(cardId1, cardPin1)
        println("---")
        println("Account 1 Balance")
        assertEquals(100.0f, bank.loggedInAs!!.accountBalance, "Transfer receive error. Expected 100.0, actual ${bank.loggedInAs!!.accountBalance}")
    }
}