import org.junit.jupiter.api.Test

class BankSystemTests {

    var bank: Bank = Bank()

    @Test
    fun createAccounts() {
        bank.register(Account().apply {
            firstName = "Max"
            lastName = "Mustermann"
            pin = "123456"
        })

        Thread.sleep(1500)

        bank.register(Account().apply {
            firstName = "Maria"
            lastName = "Musterfrau"
            pin = "456789"
        })

        bank.getAccountInfo()
    }

    @Test
    fun registerLogoutLogin() {
        bank.register(Account().apply {
            firstName = "Max"
            lastName = "Mustermann"
            pin = "123456"
        })

        val credId = bank.loggedInAs!!.accountIdentifier
        val credPin = bank.loggedInAs!!.pin

        bank.logout()
        bank.login(credId, credPin)
    }
}