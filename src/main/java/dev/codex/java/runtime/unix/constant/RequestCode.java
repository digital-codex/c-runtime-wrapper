package dev.codex.java.runtime.unix.constant;

public final class RequestCode {
    private enum Direction {
        NONE, WRITE, READ;

        public int value() {
            return this.ordinal();
        }
    }

    public static final RequestCode SET_NO_CHECK_SUM = new RequestCode(Direction.WRITE.value(), (byte) 'T', 200, 4);
    public static final RequestCode SET_DEBUG = new RequestCode(Direction.WRITE.value(), (byte) 'T', 201, 4);
    public static final RequestCode SET_INTERFACE = new RequestCode(Direction.WRITE.value(), (byte) 'T', 202, 4);
    public static final RequestCode SET_PERSIST = new RequestCode(Direction.WRITE.value(), (byte) 'T', 203, 4);
    public static final RequestCode SET_OWNER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 204, 4);
    public static final RequestCode SET_LINK = new RequestCode(Direction.WRITE.value(), (byte) 'T', 205, 4);
    public static final RequestCode SET_GROUP = new RequestCode(Direction.WRITE.value(), (byte) 'T', 206, 4);
    public static final RequestCode GET_FEATURES = new RequestCode(Direction.READ.value(), (byte) 'T', 207, 4);
    public static final RequestCode SET_OFFLOAD = new RequestCode(Direction.WRITE.value(), (byte) 'T', 208, 4);
    public static final RequestCode SET_TRANSMITTER_FILTER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 209, 4);
    public static final RequestCode GET_INTERFACE = new RequestCode(Direction.READ.value(), (byte) 'T', 210, 4);
    public static final RequestCode GET_SEND_BUFFER = new RequestCode(Direction.READ.value(), (byte) 'T', 211, 4);
    public static final RequestCode SET_SEND_BUFFER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 212, 4);
    public static final RequestCode SET_ATTACH_FILTER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 213, 16);
    public static final RequestCode SET_DETACH_FILTER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 214, 16);
    public static final RequestCode GET_VIRTUAL_NETWORK_HIGH_DATA_RATES = new RequestCode(Direction.READ.value(), (byte) 'T', 215, 4);
    public static final RequestCode SET_VIRTUAL_NETWORK_HIGH_DATA_RATES = new RequestCode(Direction.WRITE.value(), (byte) 'T', 216, 4);
    public static final RequestCode SET_QUEUE = new RequestCode(Direction.WRITE.value(), (byte) 'T', 217, 4);
    public static final RequestCode SET_INTERFACE_INDEX = new RequestCode(Direction.WRITE.value(), (byte) 'T', 218, 4);
    public static final RequestCode GET_FILTER = new RequestCode(Direction.READ.value(), (byte) 'T', 219, 16);
    public static final RequestCode SET_VIRTUAL_NETWORK_LITTLE_ENDIAN = new RequestCode(Direction.WRITE.value(), (byte) 'T', 220, 4);
    public static final RequestCode GET_VIRTUAL_NETWORK_LITTLE_ENDIAN = new RequestCode(Direction.READ.value(), (byte) 'T', 221, 4);
    public static final RequestCode SET_VIRTUAL_NETWORK_BIG_ENDIAN = new RequestCode(Direction.WRITE.value(), (byte) 'T', 222, 4);
    public static final RequestCode GET_VIRTUAL_NETWORK_BIG_ENDIAN = new RequestCode(Direction.READ.value(), (byte) 'T', 223, 4);
    public static final RequestCode SET_PACKET_STEERING = new RequestCode(Direction.READ.value(), (byte) 'T', 224, 4);
    public static final RequestCode SET_PACKET_FILTER = new RequestCode(Direction.READ.value(), (byte) 'T', 225, 4);
    public static final RequestCode SET_CARRIER = new RequestCode(Direction.WRITE.value(), (byte) 'T', 226, 4);
    public static final RequestCode GET_DEVICE_NETWORK_NAMESPACE = new RequestCode(Direction.NONE.value(), (byte) 'T', 227, 0);

    private final long value;

    private RequestCode(long direction, byte type, long number, long size) {
        this.value = (direction << 30) | (type << 8) | number | (size << 16);
    }

    public long value() {
        return this.value;
    }
}