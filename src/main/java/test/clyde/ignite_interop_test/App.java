package test.clyde.ignite_interop_test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgnitionEx;

import test.clyde.ignite_interop_test.model.Organization;

/**
 * Hello world!
 *
 */
public class App {
	private static final String CACHE_NAME = "test";

	public static void main(String[] args) throws Exception {
		interop();
	}

	private static void interop() throws Exception {
		IgniteConfiguration cfg = IgnitionEx.loadConfiguration("ignite.xml").get1();
		Ignition.setClientMode(true);

		try (Ignite ignite = Ignition.start(cfg)) {
			IgniteCache<Integer, Organization> cache = ignite.getOrCreateCache("atomic");
			getAllCpp(cache);
		}
	}

	private static void getAllCpp(IgniteCache<Integer, Organization> cache) {
		System.out.println(">>> getAllCpp...");

		// Bulk-get values from cache.
		Map<Integer, Organization> vals = cache.getAll(new HashSet<>(Arrays.asList(1, 2)));
		for (Map.Entry<Integer, Organization> entry : vals.entrySet()) {
			System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
		}
	}

	private static void test() throws Exception {
		IgniteConfiguration cfg = IgnitionEx.loadConfiguration("ignite.xml").get1();
		Ignition.setClientMode(true);

		try (Ignite ignite = Ignition.start(cfg)) {
			IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME);
			// Individual puts and gets.
			putGet(cache);

			// Bulk puts and gets.
			putAllGetAll(cache);

		}
	}

	private static void putGet(IgniteCache<Integer, String> cache) throws IgniteException {
		System.out.println();
		System.out.println(">>> Cache put-get example started.");

		final int keyCnt = 20;

		// Store keys in cache.
		for (int i = 0; i < keyCnt; i++)
			cache.put(i, Integer.toString(i));

		System.out.println(">>> Stored values in cache.");

		for (int i = 0; i < keyCnt; i++)
			System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
	}

	private static void putAllGetAll(IgniteCache<Integer, String> cache) throws IgniteException {
		System.out.println();
		System.out.println(">>> Starting putAll-getAll example.");

		final int keyCnt = 20;

		// Create batch.
		Map<Integer, String> batch = new HashMap<>();

		for (int i = 0; i < keyCnt; i++)
			batch.put(i, "bulk-" + Integer.toString(i));

		// Bulk-store entries in cache.
		cache.putAll(batch);

		System.out.println(">>> Bulk-stored values in cache.");

		// Bulk-get values from cache.
		Map<Integer, String> vals = cache.getAll(batch.keySet());

		for (Map.Entry<Integer, String> e : vals.entrySet())
			System.out.println("Got entry [key=" + e.getKey() + ", val=" + e.getValue() + ']');
	}
}
