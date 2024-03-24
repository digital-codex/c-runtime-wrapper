package dev.codex.java.wrapper.type;

import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class FlagSet<E extends Enum<E> & Flag> extends AbstractSet<E> {
    private final Class<E> clazz;
    private int size;
    private int value;

    public FlagSet(Class<E> clazz) {
        this.clazz = clazz;
    }

    public FlagSet(Class<E> clazz, E... flags) {
        this(clazz); this.addAll(Arrays.asList(flags));
    }

    public static <E extends Flag> int valueOf(E... flags) {
        int value = 0;
        for (E flag : flags) {
            value = value | flag.value();
        }
        return value;
    }

    public static <E extends Enum<E> & Flag> E findByValue(Class<E> clazz, int value) {
        for (E constant : clazz.getEnumConstants()) {
            if (constant.value() == value) return constant;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return (this.value & ((E) o).value()) != 0;
    }

    private class FlagSetIterator implements Iterator<E> {
        private final Class<E> clazz;
        private int copy;
        private int power;

        FlagSetIterator(Class<E> clazz) {
            this.clazz = clazz;
            this.copy = value;
            this.power = 1;

            while ((this.copy & this.power) == 0 && this.copy != 0) {
                this.copy = this.copy & (~this.power); this.power *= 2;
            }
        }

        @Override
        public boolean hasNext() {
            return this.copy  != 0;
        }

        @Override
        public E next() {
            E e = FlagSet.findByValue(this.clazz, this.copy & this.power);
            this.copy = this.copy & (~this.power);
            while ((this.copy & this.power) == 0 && this.copy != 0) {
                this.copy = this.copy & (~this.power); this.power *= 2;
            }
            return e;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new FlagSetIterator(this.clazz);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        int index = 0;

        E[] constants = this.clazz.getEnumConstants();
        for (E constant : constants) {
            if (this.contains(constant))
                array[index++] = constant;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] array = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        int index = 0;

        E[] constants = this.clazz.getEnumConstants();
        for (E constant : constants) {
            if (this.contains(constant))
                ((Object[]) array)[index++] = constant;
        }
        return array;
    }

    @Override
    public boolean add(E e) {
        if ((this.value & e.value()) != 0) {
            return false;
        }

        this.value = this.value | e.value();
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E e = (E) o;
        if ((this.value & e.value()) == 0) {
            return false;
        }

        this.value = (this.value & (~e.value()));
        --this.size;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!this.contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (this.add(e)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            if (!this.contains(e)) {
                this.remove(e);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            if (this.remove(e)) modified = true;
        }
        return modified;
    }

    @Override
    public void clear() {
        this.value = 0;
        this.size = 0;
    }

    public int value() {
        return this.value;
    }
}