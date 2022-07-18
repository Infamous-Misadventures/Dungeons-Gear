package com.infamous.dungeons_gear.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {
            //generator.addProvider(new ModLanguageProvider(generator, "en_us"));
            generator.addProvider(new ModItemModelProvider(generator, event.getExistingFileHelper()));
        }
        if (event.includeServer()) {
            ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(generator, event.getExistingFileHelper());
            generator.addProvider(modBlockTagsProvider);
            generator.addProvider(new ModItemTagsProvider(generator, modBlockTagsProvider, event.getExistingFileHelper()));
            //generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new ModChestLootTablesProvider(generator));
        }
    }
}