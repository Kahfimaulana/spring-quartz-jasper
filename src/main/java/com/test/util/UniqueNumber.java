package com.test.util;

/**
 * Created by Kahfi on 11/26/2014.
 *
 * This class is used to Generated Unique Id
 */
public class UniqueNumber {
    private static UniqueNumber instance = null;
    private long currentCounter;

    /**
     *
     */
    private UniqueNumber() {
        currentCounter = (System.currentTimeMillis() + 1) << 10;
    }

    /**
     *
     * @return unique number
     */
    private static synchronized UniqueNumber getInstance() {
        if (instance == null) {
            instance = new UniqueNumber();
        }
        return instance;
    }

    private synchronized long nextNumber() {
        currentCounter++;
        while (currentCounter > (System.currentTimeMillis() << 10)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        return currentCounter;
    }

    /**
     *
     * @return next unique number
     */
    public static long getUniqueNumber() {
        return getInstance().nextNumber();
    }
}