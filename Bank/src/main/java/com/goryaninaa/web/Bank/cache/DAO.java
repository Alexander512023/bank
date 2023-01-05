package com.goryaninaa.web.Bank.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class DAO<V> {

	private final Map<String, DataAccessStrategy> dataAccesses;
	private final List<V> dataList = new ArrayList<>();
	
	public DAO(Map<String, DataAccessStrategy> dataAccesses) {
		this.dataAccesses = dataAccesses;
	}
	
	Optional<V> getData(Object key, String dataAccessType) {
		if (!dataAccesses.containsKey(dataAccessType)) {
			throw new RuntimeException("Not supported");
		}
		DataAccessStrategy dataAccess = dataAccesses.get(dataAccessType);
		return dataAccess.getData(key, dataList);
	}

	public List<V> getDataList() {
		return dataList;
	}
}
