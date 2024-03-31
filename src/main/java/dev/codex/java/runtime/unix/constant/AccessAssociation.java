package dev.codex.java.runtime.unix.constant;

import dev.codex.java.runtime.type.Flag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class AccessAssociation {
    public static final AccessAssociation READ = new AccessAssociation(AccessCode.READ_ONLY);
    public static final AccessAssociation WRITE = new AccessAssociation(AccessCode.WRITE_ONLY, CreationFlag.CREAT, CreationFlag.TRUNCATE);
    public static final AccessAssociation APPEND = new AccessAssociation(AccessCode.WRITE_ONLY, CreationFlag.CREAT, StatusFlag.APPEND);
    public static final AccessAssociation READ_PLUS = new AccessAssociation(AccessCode.READ_WRITE);
    public static final AccessAssociation WRITE_PLUS = new AccessAssociation(AccessCode.READ_WRITE, CreationFlag.CREAT, CreationFlag.TRUNCATE);
    public static final AccessAssociation APPEND_PLUS = new AccessAssociation(AccessCode.READ_WRITE, CreationFlag.CREAT, StatusFlag.APPEND);

    public static final Map<AccessMode, AccessAssociation> associations = new HashMap<>();
    static {
        associations.put(AccessMode.READ, AccessAssociation.READ);
        associations.put(AccessMode.WRITE, AccessAssociation.WRITE);
        associations.put(AccessMode.APPEND, AccessAssociation.APPEND);
        associations.put(AccessMode.READ_PLUS, AccessAssociation.READ_PLUS);
        associations.put(AccessMode.WRITE_PLUS, AccessAssociation.WRITE_PLUS);
        associations.put(AccessMode.APPEND_PLUS, AccessAssociation.APPEND_PLUS);
    }

    private final AccessCode mode;
    private final AccessCode.OptionFlag[] flags;

    private AccessAssociation(AccessCode mode, AccessCode.OptionFlag... flags) {
        this.mode = mode;
        this.flags = flags;
    }

    public boolean validateMode(AccessCode mode) {
        return AccessCode.READ_WRITE.equals(this.mode) || Objects.equals(mode, this.mode);
    }

    public boolean validateFlags(AccessCode.OptionFlag[] options) {
        final int value = Flag.valueOf(this.flags);
        for (AccessCode.OptionFlag flag : options) {
            if ((value & flag.value()) != 0) return false;
        }
        return true;
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