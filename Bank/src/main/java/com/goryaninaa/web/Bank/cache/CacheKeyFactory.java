package com.goryaninaa.web.Bank.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

public class CacheKeyFactory {
	
	private final Map<String, KeyExtractStrategy> cacheKeyGenerationCatalog;
	
	public CacheKeyFactory(Map<String, KeyExtractStrategy> cacheKeyGeneratorCatalog) {
		this.cacheKeyGenerationCatalog = cacheKeyGeneratorCatalog;
	}

	public CacheKey generateCacheKey(Object key, String dataAccessStrategyType) {
		return new CacheKey(key, dataAccessStrategyType);
	}
	
	public List<CacheKey> generateAllCacheKeys(Object entity) {
		List<CacheKey> allPosibleKeysForEntity = new CopyOnWriteArrayList<>();
		for (Entry<String, KeyExtractStrategy> cacheKeyGeneratorSpecification : cacheKeyGenerationCatalog.entrySet()) {
			KeyExtractStrategy keyExtractStrategy = cacheKeyGeneratorSpecification.getValue();
			Object key = keyExtractStrategy.extractKey(entity);
			String dataAccessStrategyType = cacheKeyGeneratorSpecification.getKey();
			CacheKey cacheKey = generateCacheKey(key, dataAccessStrategyType);
			allPosibleKeysForEntity.add(cacheKey);
		}
		return allPosibleKeysForEntity;
	}
}
