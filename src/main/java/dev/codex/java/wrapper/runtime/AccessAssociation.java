package dev.codex.java.wrapper.runtime;

import java.util.HashMap;
import java.util.Map;

public final class AccessAssociation {
    public static final AccessAssociation READ = new AccessAssociation(AccessFlag.READ_ONLY);
    public static final AccessAssociation WRITE = new AccessAssociation(AccessFlag.WRITE_ONLY, OptionFlag.CREAT, OptionFlag.TRUNCATE);
    public static final AccessAssociation APPEND = new AccessAssociation(AccessFlag.WRITE_ONLY, OptionFlag.CREAT, OptionFlag.APPEND);
    public static final AccessAssociation READ_PLUS = new AccessAssociation(AccessFlag.READ_WRITE);
    public static final AccessAssociation WRITE_PLUS = new AccessAssociation(AccessFlag.READ_WRITE, OptionFlag.CREAT, OptionFlag.TRUNCATE);
    public static final AccessAssociation APPEND_PLUS = new AccessAssociation(AccessFlag.READ_WRITE, OptionFlag.CREAT, OptionFlag.APPEND);

    static final Map<AccessMode, AccessAssociation> associations = new HashMap<>();
    static {
        associations.put(AccessMode.READ, AccessAssociation.READ);
        associations.put(AccessMode.WRITE, AccessAssociation.WRITE);
        associations.put(AccessMode.APPEND, AccessAssociation.APPEND);
        associations.put(AccessMode.READ_PLUS, AccessAssociation.READ_PLUS);
        associations.put(AccessMode.WRITE_PLUS, AccessAssociation.WRITE_PLUS);
        associations.put(AccessMode.APPEND_PLUS, AccessAssociation.APPEND_PLUS);
    }

    private final AccessFlag mode;
    private final OptionFlag[] options;
    private final int value;

    private AccessAssociation(AccessFlag mode, OptionFlag... options) {
        this.mode = mode;
        this.options = options;
        this.value = OptionFlag.valueOf(this.options);
    }

    public boolean contains(AccessFlag mode) {
        return this.mode.value() == AccessFlag.READ_WRITE.value() || this.mode.value() == mode.value();
    }
    public boolean contains(OptionFlag option) {
        return (this.value & option.value()) != 0;
    }

    public AccessFlag mode() {
        return this.mode;
    }

    public OptionFlag[] options() {
        return this.options;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result += PRIME * this.mode.value();
        for (OptionFlag option : this.options) {
            result += PRIME * option.value();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AccessAssociation that)) return false;

        if (!this.contains(that.mode)) return false;
        for (OptionFlag option : that.options) {
            if (!this.contains(option)) return false;
        }

        return true;
    }
}