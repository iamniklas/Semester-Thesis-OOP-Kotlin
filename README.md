# Semester-Thesis-OOP-Kotlin

## 1. Description

This project is part of my semester thesis in the subject "Concepts of Programming Languages" (KP) at the Rosenheim Technical University of Applied Sciences.
The purpose of the project is to compare object-oriented programming principles in Kotlin and Golang.

The central element of the implementation is a banking system that manages different types of accounts.
There are 3 types of accounts: Basic, Standard and SuperPremium. 
Each type has a different variant of the deposit and withdraw function, whereby e.g. bonuses are awarded randomly or a fee is deducted if the account balance is lower than 0.
You can log in to the bank, register and logout. 
As mentioned, money can be withdrawn or deposited from a registered account. (Equivalent to the TransactionType ATM)
In addition, money can be transferred from one account to another registered account.
All transactions are stored in a transaction history and the last 5 can be called up in text form.

## 2. Usage

The project is written in Kotlin and uses the Gradle Build System. I've used the "IntelliJ IDEA", so the best is to use the same program for testing.
The project configuration may not be available, but you can just hit the green arrow left to 

``fun main(args: Array<String>)``

and IDEA will set up the correct configuration.

Unit Tests can be run the same way. (File: BankSystemTests.kt)

## 3. Knowledge Base

Everything I know about Kotlin comes from the development of Android apps with Android Studio and the book "Kotlin - Das umfassende Handbuch" by Michael Kofler.

[Link to Book](https://www.amazon.de/Kotlin-umfassende-Handbuch-Michael-Kofler/dp/3836272776/)