package io.collective;

import java.time.Clock;

/**
 * A simple cache implementation that supports automatic expiration of entries.
 * This cache does not use any built-in collection classes such as Lists, Maps, or Sets.
 */
public class SimpleAgedCache {
    private final Clock clock; // Clock instance for tracking expiration times
    private ExpirableEntry head; // Head of the linked list

    /**
     * Constructor that accepts a Clock instance.
     *
     * @param clock The Clock instance used for tracking expiration times.
     */
    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.head = null;
    }

    /**
     * Default constructor that uses the system default clock.
     */
    public SimpleAgedCache() {
        this(Clock.systemDefaultZone());
    }

    /**
     * Adds a new entry to the cache or updates an existing entry.
     *
     * @param key               The key associated with the entry.
     * @param value             The value to be cached.
     * @param retentionInMillis The time in milliseconds the entry should be retained.
     */
    public void put(Object key, Object value, int retentionInMillis) {
        long expirationTime = clock.millis() + retentionInMillis;
        remove(key); // Ensure key uniqueness by removing any existing entry with the same key
        ExpirableEntry newEntry = new ExpirableEntry(key, value, expirationTime);
        newEntry.next = head; // Insert the new entry at the head of the list
        head = newEntry;
    }

    /**
     * Checks if the cache is empty after removing expired entries.
     *
     * @return True if the cache is empty, false otherwise.
     */
    public boolean isEmpty() {
        removeExpiredEntries();
        return head == null;
    }

    /**
     * Returns the number of valid entries in the cache after removing expired entries.
     *
     * @return The number of valid entries in the cache.
     */
    public int size() {
        removeExpiredEntries();
        int count = 0;
        ExpirableEntry current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    /**
     * Retrieves the value associated with the given key if it hasn't expired.
     *
     * @param key The key of the entry to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the entry has expired.
     */
    public Object get(Object key) {
        removeExpiredEntries();
        ExpirableEntry current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes entries that have expired based on the current time.
     */
    private void removeExpiredEntries() {
        long currentTime = clock.millis();
        ExpirableEntry current = head;
        ExpirableEntry prev = null;

        while (current != null) {
            if (current.expirationTime < currentTime) { // If the entry has expired
                if (prev == null) {
                    head = current.next; // Remove the head entry
                } else {
                    prev.next = current.next; // Remove the current entry
                }
            } else {
                prev = current; // Move to the next entry
            }
            current = current.next;
        }
    }

    /**
     * Removes an entry with the specified key.
     *
     * @param key The key of the entry to remove.
     */
    private void remove(Object key) {
        ExpirableEntry current = head;
        ExpirableEntry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    head = current.next; // Remove the head entry
                } else {
                    prev.next = current.next; // Remove the current entry
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    /**
     * Inner class representing an entry in the cache.
     */
    private static class ExpirableEntry {
        private final Object key; // Key of the entry
        private final Object value; // Value of the entry
        private final long expirationTime; // Expiration time of the entry
        private ExpirableEntry next; // Next entry in the linked list

        /**
         * Constructor for ExpirableEntry.
         *
         * @param key            The key of the entry.
         * @param value          The value of the entry.
         * @param expirationTime The expiration time of the entry.
         */
        ExpirableEntry(Object key, Object value, long expirationTime) {
            this.key = key;
            this.value = value;
            this.expirationTime = expirationTime;
            this.next = null;
        }
    }
}
