package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.*;

public class ByteTag extends AbstractTag<Byte> {

    public ByteTag(String name, Byte value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readByte();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeByte(value);
    }

    @Override
    public TagType getType() {
        return BYTE;
    }

}
