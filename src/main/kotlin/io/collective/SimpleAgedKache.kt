package io.collective

import java.time.Clock

/**
 * A simple cache implementation that supports automatic expiration of entries.
 * This cache does not use any built-in collection classes such as Lists, Maps, or Sets.
 */
class SimpleAgedKache(private val clock: Clock = Clock.systemDefaultZone()) {
    private var head: ExpirableEntry? = null

    /**
     * Adds a new entry to the cache or updates an existing entry.
     *
     * @param key The key associated with the entry.
     * @param value The value to be cached.
     * @param retentionInMillis The time in milliseconds the entry should be retained.
     */
    fun put(key: Any, value: Any, retentionInMillis: Int) {
        val expirationTime = clock.millis() + retentionInMillis
        remove(key) // Ensure key uniqueness by removing any existing entry with the same key
        val newEntry = ExpirableEntry(key, value, expirationTime)
        newEntry.next = head // Insert the new entry at the head of the list
        head = newEntry
    }

    /**
     * Checks if the cache is empty after removing expired entries.
     *
     * @return True if the cache is empty, false otherwise.
     */
    fun isEmpty(): Boolean {
        removeExpiredEntries()
        return head == null
    }

    /**
     * Returns the number of valid entries in the cache after removing expired entries.
     *
     * @return The number of valid entries in the cache.
     */
    fun size(): Int {
        removeExpiredEntries()
        var count = 0
        var current = head
        while (current != null) {
            count++
            current = current.next
        }
        return count
    }

    /**
     * Retrieves the value associated with the given key if it hasn't expired.
     *
     * @param key The key of the entry to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the entry has expired.
     */
    fun get(key: Any): Any? {
        removeExpiredEntries()
        var current = head
        while (current != null) {
            if (current.key == key) {
                return current.value
            }
            current = current.next
        }
        return null
    }

    /**
     * Removes entries that have expired based on the current time.
     */
    private fun removeExpiredEntries() {
        val currentTime = clock.millis()
        var current = head
        var prev: ExpirableEntry? = null

        while (current != null) {
            if (current.expirationTime < currentTime) { // If the entry has expired
                if (prev == null) {
                    head = current.next // Remove the head entry
                } else {
                    prev.next = current.next // Remove the current entry
                }
            } else {
                prev = current // Move to the next entry
            }
            current = current.next
        }
    }

    /**
     * Removes an entry with the specified key.
     *
     * @param key The key of the entry to remove.
     */
    private fun remove(key: Any) {
        var current = head
        var prev: ExpirableEntry? = null

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    head = current.next // Remove the head entry
                } else {
                    prev.next = current.next // Remove the current entry
                }
                return
            }
            prev = current
            current = current.next
        }
    }

    /**
     * Inner class representing an entry in the cache.
     *
     * @param key The key of the entry.
     * @param value The value of the entry.
     * @param expirationTime The expiration time of the entry.
     */
    private class ExpirableEntry(
        val key: Any,
        val value: Any,
        val expirationTime: Long
    ) {
        var next: ExpirableEntry? = null // Next entry in the linked list
    }
}
