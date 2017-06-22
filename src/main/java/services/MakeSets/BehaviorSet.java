package services.MakeSets;

import common.domain.Behavior;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class BehaviorSet {

    public static Set<String> makeBehaviorSet(Behavior behavior) {

        Set<String> returnSet = new HashSet<>();

        List<String> files = behavior.getFiles();
        List<String> executedCommands = behavior.getExecuted_commands();
        List<String> keys = behavior.getKeys();
        List<String> mutexes = behavior.getMutexes();
        List<String> summary = behavior.getSummary();

        returnSet.addAll(files);
        returnSet.addAll(executedCommands);
        returnSet.addAll(keys);
        returnSet.addAll(mutexes);
        returnSet.addAll(summary);

        return returnSet;
    }

}
