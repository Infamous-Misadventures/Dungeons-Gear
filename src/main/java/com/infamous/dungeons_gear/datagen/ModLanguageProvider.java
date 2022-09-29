package com.infamous.dungeons_gear.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.registry.ItemRegistry.ARMORS;
import static com.infamous.dungeons_gear.registry.ItemRegistry.ARMOR_SETS;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String locale) {
        super(gen, MODID, locale);
    }

    protected void addTranslations() {
        this.addConfigOptions();
        this.addTips();
    }

    private void addTips() {
    }

    private void addConfigOptions() {
    }

    private String getNameFromId(String idString) {
        StringBuilder sb = new StringBuilder();
        String[] var3 = idString.toLowerCase().split("_");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String word = var3[var5];
            sb.append(word.substring(0, 1).toUpperCase());
            sb.append(word.substring(1));
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
