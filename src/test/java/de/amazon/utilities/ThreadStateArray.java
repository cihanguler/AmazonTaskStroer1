package de.amazon.utilities;

import java.util.ArrayList;
import java.util.List;

public class ThreadStateArray {

    private static InheritableThreadLocal<List> sessionPool = new InheritableThreadLocal<>();

    // InheritableThreadLocal  --> this is like a container, bag, pool.
    // in this pool we can have separate objects for each thread
    // for each thread, in InheritableThreadLocal we can have separate object for that thread
    // ThreadStateArray class will provide separate arrayList() object per thread

    private ThreadStateArray() {}

    public static List getArray() {
        if (sessionPool.get() == null) {
            List<Object> keysList = new ArrayList<>();
            sessionPool.set(keysList);
        }
        return sessionPool.get();
    }

    public static void removeThreadArray() {
        for (Object key : ThreadStateArray.getArray()) {
            SessionStateHandler.removeKey((String) key);
        }
        sessionPool.remove();
    }
}
