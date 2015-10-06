package ru.ensemplix.nbt.tag;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import ru.ensemplix.nbt.stream.NBTInputStream;

import java.io.FileInputStream;
import java.io.IOException;

@RunWith(Theories.class)
public class FileTest {

    @DataPoints("uncompressed")
    public static String[] uncompressed() {
        return new String[] {"servers.dat"};
    }

    @DataPoints("compressed")
    public static String[] compressed() {
        return new String[] {"bigtest.nbt", "player.dat", "level.dat"};
    }

    @Theory
    public void testUncompressed(@FromDataPoints("uncompressed") String file) throws IOException {
        new NBTInputStream(new FileInputStream("src/test/resources/" + file), false).readTag();
    }

    @Theory
    public void testCompressed(@FromDataPoints("compressed") String file) throws IOException {
        new NBTInputStream(new FileInputStream("src/test/resources/" + file), true).readTag();
    }

}
