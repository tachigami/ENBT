package ru.ensemplix.nbt.tag.banner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ensemplix.nbt.annotation.NBT;

@NoArgsConstructor
@AllArgsConstructor
public class Pattern {

    /**
     * Рисунок который нарисован на баннере.
     */
    @NBT @Getter
    private String pattern;

    /**
     * Цвет рисунка на баннере.
     */
    @NBT @Getter
    private DyeType color;

}
