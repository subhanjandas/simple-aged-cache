# Subhanjan's Expirable Cache Implementation

## Description

This project presents my implementation of a simple cache system that supports automatic expiration of entries. 

- ### Goal
  The goal is to get a clear understanding of cache mechanisms and linked list operations without relying on built-in collection classes such as Lists, Maps, or Sets.

- ### Contents
  The project includes both Java and Kotlin versions of the cache, with detailed documentation and optimized code to ensure readability for Peer-Review/GitHub.

## Project Structure

```
simple-aged-cache/
        │
        ├── src/
        │   ├── main/
        │   │   ├── java/
        │   │   │   └── io/
        │   │   │       └── collective/
        │   │   │           └── SimpleAgedCache.java
        │   │   └── kotlin/
        │   │       └── io/
        │   │           └── collective/
        │   │               └── SimpleAgedKache.kt
        │   └── test/
        │       ├── java/
        │       │   └── test/
        │       │       └── collective/
        │       │           └── SimpleAgedCacheTest.java
        │       └── kotlin/
        │           └── test/
        │               └── collective/
        │                   └── SimpleAgedKacheTest.kt
        │
        ├── build.gradle
├── settings.gradle
├── README.md
└── README_original.md
```


## Thought Process

### Understanding the Requirements

1. **No Built-in Collections**: The requirement to avoid built-in collection classes (Lists, Maps, Sets) necessitated a design using custom data structures. This led to the use of a linked list to manage cache entries.
2. **Automatic Expiration**: Entries in the cache should automatically expire after a specified time. This was achieved by associating an expiration time with each entry and regularly checking for and removing expired entries.

### Designing the Solution

1. **Data Structure**: A linked list was chosen to store cache entries. Each entry includes a key, value, expiration time, and a reference to the next entry.
2. **Operations**:
    - **Put**: Adds or updates an entry in the cache.
    - **Get**: Retrieves an entry from the cache if it hasn't expired.
    - **Size**: Returns the number of valid entries in the cache.
    - **IsEmpty**: Checks if the cache is empty.
    - **RemoveExpiredEntries**: Removes entries that have expired.

### Implementation

1. **Java and Kotlin versions**: The solution was implemented in both Java and Kotlin to showcase proficiency in both languages.
2. **Detailed Comments**: Each class and method was documented to explain its purpose and functionality.
3. **Optimizations**: The code was optimized for readability and ease of Peer-Review.

### Pre-requisites

- JDK 11
- Gradle
- IntelliJ IDEA (optional, any Java IDE)

### Installation and Setup

1. **Clone the Repository**:

   ```sh
   git clone https://github.com/subhanjandas/simple-aged-cache.git
   cd simple-aged-cache
   ```
2. **Set Up Environment**:
Ensure you have JDK 11 installed and set JAVA_HOME to point to it:
   ```sh
    export JAVA_HOME=$(/usr/libexec/java_home -v 11)
   ```
3. **Running Tests**:
   ```sh
    ./gradlew test
   ```
   
Hope you enjoy my submission and code!

Thanks, @subhanjan

