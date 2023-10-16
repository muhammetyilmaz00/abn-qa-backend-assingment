package gitlab.Issues.utils;

import java.util.concurrent.ConcurrentHashMap;

public class ContextStore {

    /**
     * A thread-local variable that holds a ConcurrentHashMap with default initialization
     */
    private static final ThreadLocal<ConcurrentHashMap<Object, Object>> map = ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * A synchronized method for adding a key-value pair to a ConcurrentHashMap stored in a thread-local variable
     */
    public static synchronized <K, V> void put(K key, V value) {
        if (key != null && value != null) map.get().put(key, value);
    }

    /**
     * A synchronized method for retrieving a value associated with a key from a ConcurrentHashMap stored in a thread-local variable
     */
    public static synchronized <K, V> V get(K key) {
        return key != null ? ((ConcurrentHashMap<K, V>) map.get()).get(key) : null;
    }

    /**
     * A synchronized method for clearing the contents of a ConcurrentHashMap stored in a thread-local variable
     */
    static synchronized void clear() {
        map.get().clear();
    }
}
