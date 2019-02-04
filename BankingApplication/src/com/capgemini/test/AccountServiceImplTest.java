package com.capgemini.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficentBalanceException;
import com.capgemini.exceptions.InsufficientOpeningBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.Accountrepo;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountServiceImplTest {
	
	AccountService accountservice;
	
	// First step for creation of dummy object
	@Mock
	Accountrepo accountRepo;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);      // Second step for creation of dummy object..
		accountservice=new AccountServiceImpl(accountRepo);
	}
	
	//Test cases for creating the account..
	
	@Test(expected=com.capgemini.exceptions.InsufficientOpeningBalanceException.class)
	public void whenTheOpeningAccountBalanceIsLessThanFiveHundred() throws InsufficientOpeningBalanceException
	{
		accountservice.createAccount(101, 200);
	}
	@Test
	public void whenTheValidInformationIsProvided() throws InsufficientOpeningBalanceException
	{
		Account account=new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepo.save(account)).thenReturn(true);
		assertEquals(account, accountservice.createAccount(101, 5000));
	}
	
	//Test cases for depositing 
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNumberDoesNotExistThenThrowException() throws InvalidAccountNumberException
	{
		accountservice.depositAmount(102, 500);
	}
	
	@Test
	public void whenTheValidInformationsIsProvided() throws InvalidAccountNumberException 
	{
		Account account=new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepo.search(101)).thenReturn(account);
		assertEquals(account.getAmount()+500, accountservice.depositAmount(101, 500));
	}
	
	// Test Cases for Withdraw..
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNumberIsInvalid() throws InsufficentBalanceException, InvalidAccountNumberException
	{
		Account account=new Account();
		account.setAccountNumber(101);
		account.setAmount(10000);
		when(accountRepo.search(101)).thenReturn(account);
		accountservice.withdrawAmount(101, 100);
	}
	@Test(expected=com.capgemini.exceptions.InsufficentBalanceException.class)
	public void whenAmountIsNotSufficientToWithDraw() throws InsufficentBalanceException,InvalidAccountNumberException
	{
		Account account=new Account();
		account.setAccountNumber(101);
		account.setAmount(10000);
		when(accountRepo.search(101)).thenReturn(account);
		accountservice.withdrawAmount(101, 102000);
	}
	@Test
	public void infoProvidedIsValid() throws InvalidAccountNumberException, InsufficentBalanceException
	{
		Account account=new Account();
		account.setAccountNumber(101);
		account.setAmount(1000);
		when(accountRepo.search(101)).thenReturn(account);
		assertEquals(account.getAmount()-1000,accountservice.withdrawAmount(101, 1000));
	}
}
