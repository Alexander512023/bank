package com.goryaninaa.web.Bank.cache;

import java.util.List;
import java.util.Optional;

public interface DataAccessStrategy {
	
	<V> Optional<V> getData(Object key, List<V> data);
	
	String getStrategy();

}
