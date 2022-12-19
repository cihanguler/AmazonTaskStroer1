package de.amazon.utilities;

import java.util.ArrayList;
import java.util.List;

public class UniqueArrayListForThread {

    private static InheritableThreadLocal<List> threadList = new InheritableThreadLocal<>();

    // InheritableThreadLocal  --> this is like a container, bag, pool.
    // in this pool we can have separate objects for each thread
    // for each thread, in InheritableThreadLocal we can have separate object for that thread
    // UniqueArrayListForThread class will provide separate arrayList() object per thread

    private UniqueArrayListForThread() {
    }
    public static List getArray() {
        if (threadList.get() == null) {
            List<Object> listOfKeys=new ArrayList<>();
            threadList.set(listOfKeys);
        }
        return threadList.get();
    }

    public static void removeThreadArray() {
        for (Object key : UniqueArrayListForThread.getArray()) {
            ThreadStateHandler.removeKey((String) key);
        }
        threadList.remove();
    }
}
