package ru.ensemplix.nbt.tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class TagTest {

    private NBTOutputStream out;
    private NBTInputStream in;

    @DataPoints("primitives")
    public static Tag[] primitives() {
        return new Tag[] {
                new ByteTag("byte", (byte) 1),
                new ShortTag("short", (short) 2),
                new IntegerTag("integer", 3),
                new LongTag("long", (long) 4),
                new FloatTag("float", (float) 5),
                new DoubleTag("double", (double) 6),
                new StringTag("string", "I Love Koala")
        };
    }

    @DataPoints("arrays")
    public static Tag[] arrays() {
        return new Tag[] {
                new ByteArrayTag("byteArray", new byte[] {0, 1, 0, 1, 2, 3, 4, 5, 6, 7}),
                new IntegerArrayTag("integerArray", new int[] {0, 1, 0, 1, 2, 3, 4, 5, 6, 7})
        };
    }

    @Before
    public void prepareStreams() throws IOException {
        File file = File.createTempFile("tagTest", ".tmp");
        file.deleteOnExit();

        out = new NBTOutputStream(new FileOutputStream(file));
        in = new NBTInputStream(new FileInputStream(file));
    }

    @Theory
    public void testPrimitiveTags(@FromDataPoints("primitives") Tag outTag) throws IOException {
        out.writeTag(outTag);
        Tag inTag = in.readTag();

        assertEquals(outTag.getName(), inTag.getName());
        assertEquals(outTag.getValue(), inTag.getValue());
        assertEquals(outTag.getType(), inTag.getType());
        assertEquals(0, in.available());
    }

    @Theory
    public void testArraysTags(@FromDataPoints("arrays") Tag outTag) throws IOException {
        out.writeTag(outTag);
        Tag inTag = in.readTag();

        assertEquals(outTag.getName(), inTag.getName());
        assertEquals(outTag.getType(), inTag.getType());

        switch(inTag.getType()) {
            case BYTE_ARRAY:
                assertArrayEquals((byte[]) outTag.getValue(), (byte[]) inTag.getValue());
                break;
            case INTEGER_ARRAY:
                assertArrayEquals((int[]) outTag.getValue(), (int[]) inTag.getValue());
                break;
            default:
                fail();
        }

        assertEquals(0, in.available());
    }

    @Test
    public void testListTag() throws IOException {
        ListTag outTag = new ListTag("list");
        List<Tag> value = outTag.getValue();

        value.add(new StringTag("I love"));
        value.add(new StringTag("Koala <3"));

        out.writeTag(outTag);
        Tag inTag = in.readTag();
        List inList = (List) inTag.getValue();

        assertEquals(outTag.getName(), inTag.getName());
        assertEquals(value.size(), inList.size());

        for (int i = 0; i < value.size(); i++) {
            Tag listTag = value.get(i);
            Tag listTagIn = (Tag) inList.get(i);

            assertEquals(listTag.getName(), listTagIn.getName());
            assertEquals(listTag.getValue(), listTagIn.getValue());
            assertEquals(listTag.getType(), listTagIn.getType());
        }

        assertEquals(outTag.getType(), inTag.getType());
        assertEquals(0, in.available());
    }

    @Test
    public void testCompoundTag() throws IOException {
        CompoundTag tag = new CompoundTag("compound");

        for(Tag primitive : primitives()) {
            tag.setTag(primitive);
        }

        for(Tag array : arrays()) {
            tag.setTag(array);
        }

        out.writeTag(tag);
        Tag inTag = in.readTag();

        assertEquals(tag.getName(), inTag.getName());
        assertEquals(tag.getValue().size(), ((Map) inTag.getValue()).size());
        assertEquals(tag.getType(), inTag.getType());
    }

    @After
    public void closeStreams() throws IOException {
        in.close();
        out.close();
    }

}
