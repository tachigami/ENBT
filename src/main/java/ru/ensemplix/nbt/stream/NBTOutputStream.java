package ru.ensemplix.nbt.stream;

import ru.ensemplix.nbt.tag.Tag;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NBTOutputStream extends DataOutputStream {

    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    public void writeTag(Tag tag) throws IOException {
        writeByte(tag.getType().ordinal());
        writeUTF(tag.getName());
        tag.writeTag(this);
    }

}
