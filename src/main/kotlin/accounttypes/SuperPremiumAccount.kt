package accounttypes

import kotlin.random.Random

class SuperPremiumAccount : Account() {
    override fun withdraw(_amount: Float, transactionType: TransactionType): Int {
        return super.withdraw(_amount, transactionType)
    }

    override fun deposit(_amount: Float, transactionType: TransactionType): Int {
        var amount = _amount

        if(Random.nextInt(1, 10) == 1) {
            amount *= 1.5f
        }

        return super.deposit(amount, transactionType)
    }
}