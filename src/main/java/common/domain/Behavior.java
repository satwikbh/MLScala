package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Behavior {

    private List<String> files;
    private List<String> keys;
    private List<String> summary;
    private List<String> mutexes;
    private List<String> executed_commands;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getSummary() {
        return summary;
    }

    public void setSummary(List<String> summary) {
        this.summary = summary;
    }

    public List<String> getMutexes() {
        return mutexes;
    }

    public void setMutexes(List<String> mutexes) {
        this.mutexes = mutexes;
    }

    public List<String> getExecuted_commands() {
        return executed_commands;
    }

    public void setExecuted_commands(List<String> executed_commands) {
        this.executed_commands = executed_commands;
    }
}
