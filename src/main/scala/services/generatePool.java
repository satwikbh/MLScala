package services;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class generatePool {

    public static Set<String> pool = new HashSet<String>();

    public static void addToPool(Set<String> subPool) {
        pool.addAll(subPool);
    }

}
