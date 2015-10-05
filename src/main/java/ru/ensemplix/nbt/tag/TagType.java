package ru.ensemplix.nbt.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

import static com.google.common.primitives.Primitives.wrap;

@AllArgsConstructor
@NoArgsConstructor
public enum TagType {

    END,
    BYTE(ByteTag.class, byte.class),
    SHORT(ShortTag.class, short.class),
    INTEGER(IntegerTag.class, int.class),
    LONG(LongTag.class, long.class),
    FLOAT(FloatTag.class, float.class),
    DOUBLE(DoubleTag.class, double.class),
    BYTE_ARRAY(ByteArrayTag.class, byte[].class),
    STRING(StringTag.class, String.class),
    LIST(ListTag.class, List.class),
    COMPOUND(CompoundTag.class, Map.class),
    INTEGER_ARRAY(IntegerArrayTag.class, int[].class);

    private static final Map<Class, TagType> types = new HashMap<>();

    @Getter
    private Class<? extends Tag> tagType;

    @Getter
    private Class<?> valueType;

    public Tag createTag() {
        return createTag("", null);
    }

    public Tag createTag(String name) {
        return createTag(name, null);
    }

    public Tag createTag(String name, Object value) {
        try {
            return tagType.getConstructor(String.class, wrap(valueType)).newInstance(name, value);
        } catch(Exception e) {
            throw new IllegalStateException("Failed create " + tagType + " tag " + name, e);
        }
    }

    public static TagType getType(int id) {
        TagType[] types = values();

        if(types.length > id) {
            return types[id];
        }

        return null;
    }

    public static TagType getType(Class<?> cls) {
        return types.get(cls);
    }

    static {
        for(TagType type : values()) {
            types.put(type.valueType, type);
        }
    }

}
