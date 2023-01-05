package com.goryaninaa.web.Bank.cache;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheKey {
	
	private final Object key;
	private final String operationType;
	private final AtomicInteger numberOfUses = new AtomicInteger(1);
	
	public CacheKey(Object key, String operationType) {
		this.key = key;
		this.operationType = operationType;
	}

	public Object getKey() {
		return key;
	}

	public String getOperationType() {
		return operationType;
	}
	
	public void use() {
		numberOfUses.incrementAndGet();
	}
	
	public int getNumberOfUses() {
		return numberOfUses.get();
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, operationType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		return Objects.equals(key, other.key) && Objects.equals(operationType, other.operationType);
	}
}
