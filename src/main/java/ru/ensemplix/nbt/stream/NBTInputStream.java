package ru.ensemplix.nbt.stream;

import ru.ensemplix.nbt.tag.Tag;
import ru.ensemplix.nbt.tag.TagType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static ru.ensemplix.nbt.tag.TagType.END;

public class NBTInputStream extends DataInputStream {

    public NBTInputStream(InputStream in) throws IOException {
        this(in, false);
    }

    public NBTInputStream(InputStream in, boolean compressed) throws IOException {
        super(compressed ? new GZIPInputStream(in) : in);
    }

    public TagType readTagType() throws IOException {
        int id = readUnsignedByte();
        TagType type = TagType.getType(id);

        if(type == null) {
            throw new IllegalStateException("Unknown NBT tag type: " + id);
        }

        return type;
    }

    public Tag readTag() throws IOException {
        TagType type = readTagType();

        if(type == END) {
            return null;
        }

        Tag tag = createTag(readUTF(), type);
        tag.readTag(this);

        return tag;
    }

    public Tag createTag(String name, TagType type) {
        try {
            return (Tag) type.getCls().getConstructors()[0].newInstance(name, null);
        } catch(Exception e) {
            throw new IllegalStateException("Failed create " + type + " tag " + name, e);
        }
    }

}
