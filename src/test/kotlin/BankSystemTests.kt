import accounttypes.StandardAccount
import org.junit.jupiter.api.Test

class BankSystemTests {

    var bank: Bank = Bank()

    @Test
    fun createAccounts() {
        bank.register(StandardAccount().apply {
            firstName = "Max"
            lastName = "Mustermann"
            pin = "123456"
        })

        Thread.sleep(1500)

        bank.register(StandardAccount().apply {
            firstName = "Maria"
            lastName = "Musterfrau"
            pin = "456789"
        })

        bank.getAccountInfo()
    }

    @Test
    fun registerLogoutLogin() {
        bank.register(StandardAccount().apply {
            firstName = "Max"
            lastName = "Mustermann"
            pin = "123456"
        })

        val credId = bank.loggedInAs!!.accountIdentifier
        val credPin = bank.loggedInAs!!.pin

        bank.logout()
        bank.login(credId, credPin)
    }

    @Test
    fun createAccountsDepositAndTransferMoney() {
        val acc1 = StandardAccount().apply {
            firstName = "Max"
            lastName = "Mustermann"
            pin = "123456"
        }
        Thread.sleep(1500)
        val acc2 = StandardAccount().apply {
            firstName = "Maria"
            lastName = "Musterfrau"
            pin = "456789"
        }

        println("---")
        bank.register(acc1)
        bank.getAccountInfo()

        println("---")
        bank.register(acc2)
        bank.deposit(100.0f)
        bank.transfer(acc1.accountIdentifier, 50.0f)
        bank.getAccountInfo()

        println("---")
        bank.logout()
        bank.login(acc1.accountIdentifier, acc1.pin)
        bank.getAccountInfo()
    }
}