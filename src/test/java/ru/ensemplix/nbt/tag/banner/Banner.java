package ru.ensemplix.nbt.tag.banner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ensemplix.nbt.annotation.NBT;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Banner extends Tile {

    /**
     * Основной цвет баннера.
     */
    @NBT @Getter
    private DyeType base = DyeType.WHITE;

    /**
     * На баннере может быть 6 различных рисунков.
     */
    @NBT @Getter
    private List<Pattern> patterns = new ArrayList<>(6);

    public Banner(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
