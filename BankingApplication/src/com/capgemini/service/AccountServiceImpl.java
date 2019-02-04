package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficentBalanceException;
import com.capgemini.exceptions.InsufficientOpeningBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.Accountrepo;

public class AccountServiceImpl implements AccountService {
	
	Accountrepo accountrepo;
	
	// Why this constructor??
	public AccountServiceImpl(Accountrepo accountrepo) {
		super();
		this.accountrepo = accountrepo;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber, int amount) throws InsufficientOpeningBalanceException {
		// TODO Auto-generated method stub
		Account account=new Account();
		if(amount<500)
		{
			throw new InsufficientOpeningBalanceException();
		}
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		return account;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#depositAmount(int, int)
	 */
	@Override
	public int  depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		Account account=new Account();
		account=accountrepo.search(accountNumber);
		if(account==null)
		{
			throw new InvalidAccountNumberException();
		}
		account.setAmount(account.getAmount()+amount);
		accountrepo.save(account);
		return account.getAmount();
	}

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)
	 */
	@Override
	public int  withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficentBalanceException {
		// TODO Auto-generated method stub
		Account account=new Account();
			account =accountrepo.search(accountNumber);
			if(account==null)
			{
				throw new InvalidAccountNumberException();
			}
			if(account.getAmount()<amount)
			{
				throw new InsufficentBalanceException();
			}
			account.setAmount(account.getAmount()-amount);
			accountrepo.save(account);
			return account.getAmount();
	}
}
