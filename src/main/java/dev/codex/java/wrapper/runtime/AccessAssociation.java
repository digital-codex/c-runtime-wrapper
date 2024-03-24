package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.FlagSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class AccessAssociation {
    public static final AccessAssociation READ = new AccessAssociation(AccessFlag.READ_ONLY);
    public static final AccessAssociation WRITE = new AccessAssociation(AccessFlag.WRITE_ONLY, OptionFlag.CREAT, OptionFlag.TRUNCATE);
    public static final AccessAssociation APPEND = new AccessAssociation(AccessFlag.WRITE_ONLY, OptionFlag.CREAT, OptionFlag.APPEND);
    public static final AccessAssociation READ_PLUS = new AccessAssociation(AccessFlag.READ_WRITE);
    public static final AccessAssociation WRITE_PLUS = new AccessAssociation(AccessFlag.READ_WRITE, OptionFlag.CREAT, OptionFlag.TRUNCATE);
    public static final AccessAssociation APPEND_PLUS = new AccessAssociation(AccessFlag.READ_WRITE, OptionFlag.CREAT, OptionFlag.APPEND);

    public static final Map<AccessMode, AccessAssociation> associations = new HashMap<>();
    static {
        associations.put(AccessMode.READ, AccessAssociation.READ);
        associations.put(AccessMode.WRITE, AccessAssociation.WRITE);
        associations.put(AccessMode.APPEND, AccessAssociation.APPEND);
        associations.put(AccessMode.READ_PLUS, AccessAssociation.READ_PLUS);
        associations.put(AccessMode.WRITE_PLUS, AccessAssociation.WRITE_PLUS);
        associations.put(AccessMode.APPEND_PLUS, AccessAssociation.APPEND_PLUS);
    }

    private final AccessFlag mode;
    private final OptionFlag[] flags;

    private AccessAssociation(AccessFlag mode, OptionFlag... flags) {
        this.mode = mode;
        this.flags = flags;
    }

    public boolean validateMode(AccessFlag mode) {
        return AccessFlag.READ_WRITE.equals(this.mode) || Objects.equals(mode, this.mode);
    }

    public boolean validateFlags(FlagSet<OptionFlag> options) {
        return options.containsAll(Arrays.asList(this.flags));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AccessAssociation that)) return false;
        return this.mode == that.mode && Arrays.equals(this.flags, that.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mode, Arrays.hashCode(this.flags));
    }
}