package com.goryaninaa.web.Bank.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Cache <V> {
	
	private final Map<CacheKey, Map<CacheKey, Future<Optional<V>>>> cacheStorage = new ConcurrentHashMap<>();
	private final DAO<V> dao;
	private final ExecutorService cacheCleanerExecutor;
	private final int UNDERUSED = 3;
	private final int cacheSize;
	
	public Cache(DAO<V> dao, int cacheSize) {
		this.dao = dao;
		this.cacheSize = cacheSize;
		this.cacheCleanerExecutor = Executors.newSingleThreadExecutor();
		cacheCleanerExecutor.submit(() -> runCleanUp());
	}

	public Optional<V> getData(CacheKey key) {
		try {
			Optional<Future<Optional<V>>> data = Optional.ofNullable(getDataFromCache(key));
			if (data.isEmpty()) {
				data = getDataFromDAOThroughCache(key);
			} else {
				useKey(key);
			}
			cleanCacheIfThereIsNoDataInDAO(key, data);
			return data.get().get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void remove(List<CacheKey> cacheKeys) {
		for (CacheKey cacheKey : cacheKeys) {
			cacheStorage.remove(cacheKey);
		}
	}

	private void cleanCacheIfThereIsNoDataInDAO(CacheKey key, Optional<Future<Optional<V>>> data)
			throws InterruptedException, ExecutionException {
		if (data.get().get().isEmpty()) {
			cacheStorage.remove(key);
		}
	}

	private Optional<Future<Optional<V>>> getDataFromDAOThroughCache(CacheKey key) {
		Optional<Future<Optional<V>>> data;
		Callable<Optional<V>> getFromDAO = getFromDAO(key);
		FutureTask<Optional<V>> getFromDAOTask = new FutureTask<>(getFromDAO);
		Optional<Map<CacheKey, Future<Optional<V>>>> cachedDataWithCacheKeyFromCache =
				Optional.ofNullable(cacheStorage.putIfAbsent(key, new ConcurrentHashMap<>(Map.of(key, getFromDAOTask))));
		if (cachedDataWithCacheKeyFromCache.isPresent()) {
			data = Optional.ofNullable(cachedDataWithCacheKeyFromCache.get().get(key));
		} else {
			data = Optional.empty();
		}
		if (data.isEmpty()) {
			data = Optional.ofNullable(getFromDAOTask);
			getFromDAOTask.run();
		}
		return data;
	}
	
	private Future<Optional<V>> getDataFromCache(CacheKey key) {
		Optional<Map<CacheKey, Future<Optional<V>>>> cachedDataWithCacheKey = Optional.ofNullable(cacheStorage.get(key));
		Future<Optional<V>> cachedData = cachedDataWithCacheKey.isPresent() ? cachedDataWithCacheKey.get().get(key) : null;
		return cachedData;
	}

	private Callable<Optional<V>> getFromDAO(CacheKey key) {
		Callable<Optional<V>> getFromDAO = new Callable<Optional<V>>() {
			@Override
			public Optional<V> call() throws InterruptedException {
				return dao.getData(key.getKey(), key.getOperationType());
			}
		};
		return getFromDAO;
	}
	
	private void runCleanUp() {
		while(true) {
			cleanCache();
		}
	}

	private void cleanCache() {
		if (cacheStorage.size() > cacheSize) {
			if (cacheStorage.size() - countUnderused() < cacheSize) {
				cleanBelow(UNDERUSED);
			} else {
				cleanBelow(defineMedian());
			}
		}
		sleep(1000);
	}

	private void useKey(CacheKey key) {
		CacheKey cacheKey = cacheStorage.get(key).entrySet().stream().findFirst().get().getKey();
		cacheKey.use();
	}
	
	private void sleep(int miliseconds) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cleanBelow(int value) {
		int countBeforeHalf = cacheStorage.size() / 2;
		for (Entry<CacheKey, Map<CacheKey, Future<Optional<V>>>> cachedElement : cacheStorage.entrySet()) {
			if (cachedElement.getKey().getNumberOfUses() < value) {
				cacheStorage.remove(cachedElement.getKey());
				countBeforeHalf--;
			}
			if (countBeforeHalf == 0) {
				break;
			}
		}
	}

	private int defineMedian() {
		int totalSum = 0;
		for (Entry<CacheKey, Map<CacheKey, Future<Optional<V>>>> cachedElement : cacheStorage.entrySet()) {
			totalSum += cachedElement.getKey().getNumberOfUses();
		}
		return totalSum / cacheStorage.size();
	}

	private int countUnderused() {
		int underusedNumber = 0;
		for (Entry<CacheKey, Map<CacheKey, Future<Optional<V>>>> cachedElement : cacheStorage.entrySet()) {
			if (cachedElement.getKey().getNumberOfUses() < UNDERUSED) {
				underusedNumber++;
			}
		}
		return underusedNumber;
	}
}
