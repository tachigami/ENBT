package ru.ensemplix.nbt.stream;

import ru.ensemplix.nbt.converter.ObjectConverter;
import ru.ensemplix.nbt.tag.CompoundTag;
import ru.ensemplix.nbt.tag.Tag;
import ru.ensemplix.nbt.tag.TagType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static ru.ensemplix.nbt.tag.TagType.END;
import static ru.ensemplix.nbt.tag.TagType.getType;

public class NBTInputStream extends DataInputStream {

    public NBTInputStream(InputStream in) throws IOException {
        this(in, false);
    }

    public NBTInputStream(InputStream in, boolean compressed) throws IOException {
        super(compressed ? new GZIPInputStream(in) : in);
    }

    public TagType readTagType() throws IOException {
        int id = readUnsignedByte();
        TagType type = getType(id);

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

        Tag tag = type.createTag(readUTF());
        tag.readTag(this);

        return tag;
    }

    public <T> T readObject(Class<T> cls) throws IOException {
        try {
            return ObjectConverter.toObject(cls, (CompoundTag) readTag());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed read NBT object " + cls.getName(), e);
        }
    }

}
