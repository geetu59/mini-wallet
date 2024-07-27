package com.flipkart.wallet;

import com.flipkart.wallet.service.*;

public class WalletApplication {

	public static void main(String[] args) throws Exception {
		UserService userService = new UserService();
		TransactionService transactionService = new TransactionServiceImpl();
		WalletServiceImpl walletService = new WalletServiceImpl(userService, transactionService);

		walletService.addRule("Credit Card", new CreditCardRule(3));
		walletService.addRule("Debit Card", new DebitCardRule(3, 1.0));

		userService.registerUser("Alice");
		userService.registerUser("Bob");

		try {
			walletService.topUpWallet("Alice", PaymentMethod.CC, 5000);
			walletService.topUpWallet("Alice", PaymentMethod.CC,2000);
			walletService.topUpWallet("Alice", PaymentMethod.CC,3000);
			walletService.topUpWallet("Geet", PaymentMethod.CC,1000);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			walletService.topUpWallet("Bob", PaymentMethod.DC, 2000);
			walletService.topUpWallet("Bob", PaymentMethod.DC,2000);
			walletService.topUpWallet("Bob", PaymentMethod.DC,2000);
			walletService.topUpWallet("Bob", PaymentMethod.DC,2000);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		walletService.sendMoney("Alice", "Bob", 1000);
		walletService.createDeposit("Alice", 2000);

		System.out.println("Alice Balance: " + walletService.fetchBalance("Alice"));
		System.out.println("Bob Balance: " + walletService.fetchBalance("Bob"));

		walletService.bookDeposit("Alice");

		System.out.println("Alice Balance after dissolving FD: " + walletService.fetchBalance("Alice"));
	}

}
