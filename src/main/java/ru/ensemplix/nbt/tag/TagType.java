package ru.ensemplix.nbt.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public enum TagType {

    END,
    BYTE(ByteTag.class),
    SHORT(ShortTag.class),
    INTEGER(IntegerTag.class),
    LONG(LongTag.class),
    FLOAT(FloatTag.class),
    DOUBLE(DoubleTag.class),
    BYTE_ARRAY(ByteArrayTag.class),
    STRING(StringTag.class),
    LIST(ListTag.class),
    COMPOUND(CompoundTag.class),
    INTEGER_ARRAY(IntegerArrayTag.class);

    @Getter
    private Class<? extends Tag> cls;

    private static final List<TagType> types = new ArrayList<>();

    static {
        Collections.addAll(types, values());
    }

    public static TagType getType(int id) {
        return types.get(id);
    }

}
