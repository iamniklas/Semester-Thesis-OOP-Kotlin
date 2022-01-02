package accounttypes

class StandardAccount : Account() {
    override fun addRegistrationAmount() {
        accountBalance += 50.0f
    }

    override fun withdraw(_amount: Float, transactionType: TransactionType): Int {
        var amount = _amount
        if(accountBalance < 0.0f) {
            amount *= 1.1f
        }
        return super.withdraw(_amount, transactionType)
    }

    override fun deposit(_amount: Float, transactionType: TransactionType): Int {
        return super.deposit(_amount, transactionType)
    }
}