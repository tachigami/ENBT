package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.*;

public class LongTag extends AbstractTag<Long> {

    public LongTag(String name, Long value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readLong();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeLong(value);
    }

    @Override
    public TagType getType() {
        return LONG;
    }

}
