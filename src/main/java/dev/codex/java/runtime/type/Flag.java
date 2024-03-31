package dev.codex.java.runtime.type;

public interface Flag {
    int value();

    @SafeVarargs
    static <E extends Flag> int valueOf(E... flags) {
        int value = 0;
        for (E flag : flags) {
            value = value | flag.value();
        }
        return value;
    }
}