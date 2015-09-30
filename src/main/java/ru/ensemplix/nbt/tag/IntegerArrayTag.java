package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.*;

public class IntegerArrayTag extends AbstractTag<int[]> {

    public IntegerArrayTag(String name, int[] value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        int length = in.readInt();
        value = new int[length];

        for(int i = 0; i < length; i++) {
            value[i] = in.readInt();
        }
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeInt(value.length);

        for(int i = 0; i < value.length; i++) {
            out.writeInt(value[i]);
        }
    }

    @Override
    public TagType getType() {
        return INTEGER_ARRAY;
    }

}
