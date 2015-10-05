package ru.ensemplix.nbt.tag;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.tag.servers.Server;
import ru.ensemplix.nbt.tag.servers.ServerList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    private NBTInputStream in;

    @Test
    public void testServers() throws IOException {
        in = new NBTInputStream(new FileInputStream("src/test/resources/servers.dat"), false);

        ServerList serverList = in.readObject(ServerList.class);
        assertNotNull(serverList);

        List<Server> servers = serverList.servers;

        assertNotNull(servers);
        assertEquals(1, servers.size());
        assertEquals("Ensemplix Sandbox", servers.get(0).name);
        assertEquals("sv1.ensemplix.ru:25565", servers.get(0).ip);
        assertEquals(false, servers.get(0).hideAddress);
    }

    @After
    public void closeStream() throws IOException {
        if(in != null) {
            in.close();
        }
    }

}
