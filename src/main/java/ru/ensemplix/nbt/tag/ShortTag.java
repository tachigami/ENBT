package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.SHORT;

public class ShortTag extends AbstractTag<Short> {

    public ShortTag(String name, Short value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readShort();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeShort(value);
    }

    @Override
    public TagType getType() {
        return SHORT;
    }

}
