package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class ArtifactHelper {
    public static List<Item> getArtifactList(){
        return ItemRegistry.ARTIFACTS.values().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
