package de.amazon.utilities;

import java.util.HashMap;

public class ThreadStateHandler {

    private static HashMap<String, Object> state = new HashMap<String, Object>();

    public static <T> T getValue(String key) {
        if (state.containsKey(key)) {
            return (T) state.get(key);
        }
        return null;
    }

    public static <T> void setValue(String key, T value) {
        state.put(key, value);
    }

    /**
     * to remove specific key in memory
     *
     * @param key
     */
    public static void removeKey(String key) {
        state.remove(key);
    }

    /** to clear all key in memory */
    public static void clear() {
        state = new HashMap<String, Object>();
    }

    public static void setValue(String secondData) {}
}
