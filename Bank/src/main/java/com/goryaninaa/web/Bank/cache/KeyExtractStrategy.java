package com.goryaninaa.web.Bank.cache;

public interface KeyExtractStrategy {

	Object extractKey(Object entity);
	
	String getStrategy();
	
}
