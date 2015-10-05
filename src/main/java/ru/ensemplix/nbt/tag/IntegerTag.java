package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.INTEGER;

public class IntegerTag extends AbstractTag<Integer> {

    public IntegerTag(String name, Integer value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readInt();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeInt(value);
    }

    @Override
    public TagType getType() {
        return INTEGER;
    }

}
