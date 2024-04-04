package dev.codex.java.runtime.unix;

import dev.codex.java.runtime.exception.InvalidBufferLengthException;
import dev.codex.java.runtime.type.*;
import dev.codex.java.runtime.type.NativeError;
import dev.codex.java.runtime.unix.constant.*;

import java.util.Objects;

public final class NativeRuntimeWrapper {
    private NativeRuntimeWrapper() {
        super();
    }

    public static FileDescriptor open(String pathname, AccessCode.OptionFlag...flags) throws NativeError {
        return open(pathname, AccessCode.READ_ONLY, flags);
    }

    // TODO: Set mode argument on O_CREAT being passed to flags
    public static FileDescriptor open(String pathname, AccessCode mode, AccessCode.OptionFlag...flags) throws NativeError {
        final int value = Flag.valueOf(flags) | mode.value();
        int fd = FileControl.open(pathname, Flag.valueOf(flags) | mode.value());
        if (fd < 0) {
            throw NativeRuntimeWrapper.perror("open");
        }

        return new FileDescriptor(fd, mode, flags);
    }

    public static FileDescriptor creat(String pathname) throws NativeError {
        return NativeRuntimeWrapper.creat(pathname, AccessCode.WRITE_ONLY);
    }

    public static FileDescriptor creat(String pathname, AccessCode mode) throws NativeError {
        return open(pathname, mode, CreationFlag.CREAT, CreationFlag.TRUNCATE);
    }

    public static FileDescriptor openat(FileDescriptor dirfd, String pathname, AccessCode.OptionFlag... flags) throws NativeError {
        return openat(dirfd, pathname, AccessCode.READ_ONLY, flags);
    }

    public static FileDescriptor openat(FileDescriptor dirfd, String pathname, AccessCode mode, AccessCode.OptionFlag... flags) throws NativeError {
        int fd = FileControl.openat(dirfd.fd(), pathname, Flag.valueOf(flags) | mode.value());
        if (fd < 0) {
            throw NativeRuntimeWrapper.perror("openat");
        }
        return new FileDescriptor(fd, mode, flags);
    }

    public static int ioctl(FileDescriptor fd, RequestCode code, InterfaceRequest request) throws NativeError {
        int ret = IOControl.ioctl(fd.fd(), code.value(), request.address().value());
        if (ret < 0) {
            throw NativeRuntimeWrapper.perror("ioctl");
        }
        return ret;
    }

    public static void fclose(FileStream stream) throws NativeError {
        if (StandardIO.fclose(stream.address().value()) < 0) {
            throw NativeRuntimeWrapper.perror("fclose");
        }
    }

    public static FileStream fopen(String pathname, AccessMode mode) throws NativeError {
        MemoryAddress address = MemoryAddress.of(StandardIO.fopen(pathname, mode.value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw NativeRuntimeWrapper.perror("fopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static FileStream fdopen(FileDescriptor fd, AccessMode mode) throws NativeError {
        AccessAssociation association = AccessAssociation.associations.getOrDefault(mode, AccessAssociation.READ);

        if (!association.validateMode(fd.mode()) || !association.validateFlags(fd.options())) {
            throw NativeError.INCOMPATIBLE_ACCESS_ASSOCIATION;
        }

        MemoryAddress address = MemoryAddress.of(StandardIO.fdopen(fd.fd(), mode.value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw NativeRuntimeWrapper.perror("fdopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static FileStream freopen(String pathname, AccessMode mode, FileStream stream) throws NativeError {
        MemoryAddress address = MemoryAddress.of(StandardIO.freopen(pathname, mode.value(), stream.address().value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw NativeRuntimeWrapper.perror("freopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static long fread(byte[] ptr, long size, long nmemb, FileStream stream) throws NativeError {
        validateBufferLength(size, ptr.length, "size", "greater than `ptr` length");

        long n = StandardIO.fread(ptr, size, nmemb, stream.address().value());
        if (n < 0) {
            throw NativeRuntimeWrapper.perror("fread");
        }
        return n;
    }

    public static long fwrite(byte[] ptr, long size, long nmemb, FileStream stream) throws NativeError {
        validateBufferLength(size, ptr.length, "size", "greater than `ptr` length");

        long n = StandardIO.fwrite(ptr, size, nmemb, stream.address().value());
        if (n < 0) {
            throw NativeRuntimeWrapper.perror("fwrite");
        }
        return n;
    }

    public static void fseek(FileStream stream, long offset, Whence whence) throws NativeError {
        if (StandardIO.fseek(stream.address().value(), offset, whence.value()) < 0) {
            throw NativeRuntimeWrapper.perror("fseek");
        }
    }

    public static long ftell(FileStream stream) throws NativeError {
        long pos = StandardIO.ftell(stream.address().value());
        if (pos < 0) {
            throw NativeRuntimeWrapper.perror("ftell");
        }
        return pos;
    }

    public static void rewind(FileStream stream) throws NativeError {
        NativeRuntimeWrapper.fseek(stream, 0, Whence.SEEK_SET);
    }

    public static void fgetpos(FileStream stream, FileStream.Position pos) throws NativeError {
        if (StandardIO.fgetpos(stream.address().value(), pos.address().value()) < 0) {
            throw NativeRuntimeWrapper.perror("fgetpos");
        }
    }

    public static void fsetpos(FileStream stream, FileStream.Position pos) throws NativeError {
        if (StandardIO.fsetpos(stream.address().value(), pos.address().value()) < 0) {
            throw NativeRuntimeWrapper.perror("fsetpos");
        }
    }

    public static <T extends Pointer> T malloc(Class<T> clazz) throws NativeError {
        MemoryAddress ptr = MemoryAddress.of(StandardLibrary.malloc(PointerFactory.sizeof(clazz)));
        if (MemoryAddress.NULL.equals(ptr)) {
            throw NativeRuntimeWrapper.perror("malloc");
        }

        return PointerFactory.instantiate(clazz).with(ptr).build();
    }

    public static void free(Pointer pointer) {
        StandardLibrary.free(pointer.address().value());
    }

    public static <T extends Pointer> T calloc(long nmemb, Class<T> clazz) throws NativeError {
        if (nmemb <= 0) {
            throw NativeError.ILLEGAL_NUMBER_OF_ELEMENTS;
        }

        MemoryAddress ptr = MemoryAddress.of(StandardLibrary.calloc(nmemb, PointerFactory.sizeof(clazz)));
        if (MemoryAddress.NULL.equals(ptr)) {
            throw NativeRuntimeWrapper.perror("calloc");
        }

        return PointerFactory.instantiate(nmemb, clazz).with(ptr).build();
    }

    public static Pointer realloc(Pointer ptr, long size) throws NativeError {
        MemoryAddress oldAddress = ptr.address();
        if (MemoryAddress.NULL.equals(oldAddress)) {
            return NativeRuntimeWrapper.malloc(ptr.getClass());
        }

        if (size == 0) {
            NativeRuntimeWrapper.free(ptr);
            return null;
        }

        MemoryAddress newAddress = MemoryAddress.of(StandardLibrary.realloc(oldAddress.value(), size));
        if (MemoryAddress.NULL.equals(newAddress)) {
            throw NativeRuntimeWrapper.perror("realloc");
        }

        if (Objects.equals(oldAddress, newAddress)) {
            ptr.setSize(size);
            return ptr;
        }

        return PointerFactory.instantiate(ptr.getClass()).with(newAddress, size).build();
    }

    public static Pointer reallocarray(Pointer ptr, long nmemb, long size) throws NativeError {
        return NativeRuntimeWrapper.realloc(ptr, nmemb * size);
    }

    public static void print(String string) {
        if (StandardIO.print(string) < 0) {
            throw NativeRuntimeWrapper.perror("print");
        }
    }

    public static void println(String string) {
        if (StandardIO.println(string) < 0) {
            throw NativeRuntimeWrapper.perror("println");
        }
    }

    public static NativeError perror(String s) {
        String msg = new String(Strings.strerror());
        if (s != null && !s.equals("\0")) {
            msg = NativeError.MESSAGE_FORMAT.formatted(s, msg);
            return new NativeError(msg);
        }
        return new NativeError(msg);
    }

    public static void close(FileDescriptor fd) throws NativeError {
        if (UnixStandard.close(fd.fd()) < 0) {
            throw NativeRuntimeWrapper.perror("close");
        }
    }

    public static long read(FileDescriptor fd, byte[] buf, long count) throws NativeError {
        validateBufferLength(count, buf.length, "count", "greater than `buf` length");

        long n = UnixStandard.read(fd.fd(), buf, count);
        if (n < 0) {
            throw NativeRuntimeWrapper.perror("read");
        }
        return n;
    }

    public static long write(FileDescriptor fd, byte[] buf, long count) throws NativeError {
        validateBufferLength(count, buf.length, "count", "greater than `buf` length");

        long n = UnixStandard.write(fd.fd(), buf, count);
        if (n < 0) {
            throw NativeRuntimeWrapper.perror("write");
        }
        return n;
    }

    private static native void initialize();
    static {
        NativeRuntimeWrapper.initialize();
    }

    private static void validateBufferLength(long expected, long actual, String arg, String msg) {
        if (expected > actual) {
            throw new InvalidBufferLengthException(arg, msg);
        }
    }
}