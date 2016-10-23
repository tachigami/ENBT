package ru.ensemplix.nbt.tag;

import org.junit.After;
import org.junit.Test;
import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;
import ru.ensemplix.nbt.tag.banner.Banner;
import ru.ensemplix.nbt.tag.banner.Pattern;
import ru.ensemplix.nbt.tag.servers.Server;
import ru.ensemplix.nbt.tag.servers.ServerList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.ensemplix.nbt.tag.banner.DyeType.CYAN;
import static ru.ensemplix.nbt.tag.banner.DyeType.WHITE;
import static ru.ensemplix.nbt.tag.banner.PatternTypes.BASE;

public class ObjectTest {

    private NBTInputStream in;
    private NBTOutputStream out;

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
        assertEquals(true, servers.get(0).hideAddress);
    }

    @Test
    public void testServers2() throws IOException {
        File file = File.createTempFile("serversTest", ".tmp");
        file.deleteOnExit();

        in = new NBTInputStream(new FileInputStream(file));
        out = new NBTOutputStream(new FileOutputStream(file));

        ServerList outServerList = new ServerList();
        outServerList.servers.add(new Server("Ensemplix Sandbox", "sv1.ensemplix.ru:25565", true));

        out.writeObject(outServerList);

        ServerList inServerList = in.readObject(ServerList.class);
        List<Server> servers = inServerList.servers;

        assertNotNull(servers);
        assertEquals(1, servers.size());
        assertEquals("Ensemplix Sandbox", servers.get(0).name);
        assertEquals("sv1.ensemplix.ru:25565", servers.get(0).ip);
        assertEquals(true, servers.get(0).hideAddress);
    }

    @Test
    public void testBanner() throws IOException {
        File file = File.createTempFile("bannerTest", ".tmp");
        file.deleteOnExit();

        in = new NBTInputStream(new FileInputStream(file));
        out = new NBTOutputStream(new FileOutputStream(file));

        Banner outBanner = new Banner(1, 61, -1);
        outBanner.getPatterns().add(new Pattern(BASE, CYAN));
        out.writeObject(outBanner);

        Banner inBanner = in.readObject(Banner.class);
        assertEquals(1, inBanner.x);
        assertEquals(61, inBanner.y);
        assertEquals(-1, inBanner.z);

        assertEquals(WHITE, inBanner.getBase());
        assertEquals(CYAN, inBanner.getPatterns().get(0).getColor());
        assertEquals(BASE, inBanner.getPatterns().get(0).getPattern());
    }

    @Test
    public void testBanner2() throws IOException {
        File file = File.createTempFile("bannerTest", ".tmp");
        file.deleteOnExit();

        in = new NBTInputStream(new FileInputStream(file));
        out = new NBTOutputStream(new FileOutputStream(file));

        Banner outBanner = new Banner(1, 61, -1);
        outBanner.getPatterns().add(new Pattern(BASE, CYAN));
        out.writeObject(outBanner);

        CompoundTag tag = (CompoundTag) in.readTag();
        assertEquals(1, tag.getValue("x").getValue());
        assertEquals(61, tag.getValue("y").getValue());
        assertEquals(-1, tag.getValue("z").getValue());
        assertEquals(0, tag.getValue("base").getValue());

        ListTag listTag = (ListTag) tag.getValue("patterns");
        assertEquals(1, listTag.getValue().size());

        CompoundTag patterns = (CompoundTag) listTag.getValue().get(0);
        assertEquals(9, patterns.getValue("color").getValue());
        assertEquals("b", patterns.getValue("pattern").getValue());
    }

    @After
    public void closeStream() throws IOException {
        in.close();

        if(out != null) {
            out.close();
        }
    }

}
