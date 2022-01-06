package accounttypes

class BasicAccount : Account() {
    override fun addRegistrationAmount() {
        accountBalance += 10.0f
    }

    override fun withdraw(_amount: Float, transactionType: TransactionType): Int {
        val amount = _amount * 1.05f
        return super.withdraw(amount, transactionType)
    }

    override fun deposit(_amount: Float, transactionType: TransactionType): Int {
        val amount = _amount * 0.95f
        return super.deposit(amount, transactionType)
    }
}