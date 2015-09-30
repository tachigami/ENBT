package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.*;

public class ByteArrayTag extends AbstractTag<byte[]> {

    public ByteArrayTag(String name, byte[] value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = new byte[in.readInt()];
        in.readFully(value);
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeInt(value.length);
        out.write(value);
    }

    @Override
    public TagType getType() {
        return BYTE_ARRAY;
    }

}
