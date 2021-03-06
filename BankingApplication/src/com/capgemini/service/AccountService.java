package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficentBalanceException;
import com.capgemini.exceptions.InsufficientOpeningBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientOpeningBalanceException;

	int  depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException;

	int  withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficentBalanceException;

}