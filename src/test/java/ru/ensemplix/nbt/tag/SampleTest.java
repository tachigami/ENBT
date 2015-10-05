package ru.ensemplix.nbt.tag;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;
import ru.ensemplix.nbt.tag.sample.SampleArraysAndList;
import ru.ensemplix.nbt.tag.sample.SampleCompound;
import ru.ensemplix.nbt.tag.sample.SamplePrimitives;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static ru.ensemplix.nbt.tag.sample.SampleArraysAndList.*;

public class SampleTest {

    private NBTOutputStream out;
    private NBTInputStream in;

    @Before
    public void prepareStreams() throws IOException {
        File file = File.createTempFile("sampleTest", ".tmp");
        file.deleteOnExit();

        out = new NBTOutputStream(new FileOutputStream(file));
        in = new NBTInputStream(new FileInputStream(file));
    }

    @Test
    public void testObjectPrimitives() throws IOException {
        SamplePrimitives primitives = new SamplePrimitives();
        primitives.fill();

        out.writeObject(primitives);
        assertEquals(primitives, in.readObject(SamplePrimitives.class));
    }

    @Test
    public void testTagPrimitives() throws IOException {
        SamplePrimitives primitives = new SamplePrimitives();
        primitives.fill();

        out.writeObject(primitives);
        assertPrimitives((CompoundTag) in.readTag());
    }

    @Test
    public void testArraysAndListObject() throws IOException {
        SampleArraysAndList outObject = new SampleArraysAndList();
        outObject.fill();

        out.writeObject(outObject);
        SampleArraysAndList inObject = in.readObject(SampleArraysAndList.class);

        assertArrayEquals(outObject.sampleIntegerArray, inObject.sampleIntegerArray);
        assertArrayEquals(outObject.sampleList.toArray(), inObject.sampleList.toArray());
        assertArrayEquals(outObject.sampleByteArray, inObject.sampleByteArray);
    }

    @Test
    public void testArraysAndListTags() throws IOException {
        SampleArraysAndList arraysAndList = new SampleArraysAndList();
        arraysAndList.fill();

        out.writeObject(arraysAndList);
        assertArraysAndList((CompoundTag) in.readTag());
    }

    @Test
    public void testCompoundObject() throws IOException {
        SampleCompound outObject = new SampleCompound();
        outObject.fill();

        out.writeObject(outObject);
        SampleCompound inObject = in.readObject(SampleCompound.class);

        assertEquals(outObject.primitives, inObject.primitives);
        assertArrayEquals(outObject.arraysAndList.sampleIntegerArray, inObject.arraysAndList.sampleIntegerArray);
        assertArrayEquals(outObject.arraysAndList.sampleList.toArray(), inObject.arraysAndList.sampleList.toArray());
        assertArrayEquals(outObject.arraysAndList.sampleByteArray, inObject.arraysAndList.sampleByteArray);

        for(int i = 0; i < 5; i++) {
            assertEquals(outObject.listPrimitives.get(i), inObject.listPrimitives.get(i));
        }
    }

    @Test
    public void testCompoundTag() throws IOException {
        SampleCompound compound = new SampleCompound();
        compound.fill();

        out.writeObject(compound);
        CompoundTag tag = (CompoundTag) in.readTag();

        assertPrimitives((CompoundTag) tag.getValue("primitives"));
        assertArraysAndList((CompoundTag) tag.getValue("arraysAndList"));

        for(Tag listPrimitive : ((ListTag) tag.getValue("listPrimitives")).getValue()) {
            assertPrimitives((CompoundTag) listPrimitive);
        }
    }

    public void assertPrimitives(CompoundTag tag) {
        assertNotNull(tag);

        Map<String, Tag> values = tag.getValue();

        assertNotNull(values);
        assertEquals((byte) 1, values.get("byte").getValue());
        assertEquals((short) 23, values.get("short").getValue());
        assertEquals(3.4D, values.get("double").getValue());
        assertEquals(5.6F, values.get("float").getValue());
        assertEquals(87900876675L, values.get("long").getValue());
        assertEquals("I Love Koala <3", values.get("string").getValue());
        assertNull(values.get("emptyString"));
    }

    public void assertArraysAndList(CompoundTag tag) {
        assertNotNull(tag);

        assertArrayEquals(INTEGER_ARRAY, (int[]) tag.getValue("integerArray").getValue());
        assertArrayEquals(BYTE_ARRAY, (byte[]) tag.getValue("byteArray").getValue());

        Object[] stringTags = ((List) tag.getValue("list").getValue()).toArray();
        assertEquals(STRING_ARRAY.length, stringTags.length);

        for (int i = 0; i < stringTags.length; i++) {
            Assert.assertEquals(STRING_ARRAY[i], ((StringTag) stringTags[i]).getValue());
        }
    }

    @After
    public void closeStreams() throws IOException {
        in.close();
        out.close();
    }

}
