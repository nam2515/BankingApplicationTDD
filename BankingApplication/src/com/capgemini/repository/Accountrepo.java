package com.capgemini.repository;

import com.capgemini.beans.Account;

public interface Accountrepo {
	boolean save(Account accounts);
	Account search(int accountNumber);
}
