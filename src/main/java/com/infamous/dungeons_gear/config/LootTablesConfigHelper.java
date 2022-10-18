package com.infamous.dungeons_gear.config;

import com.infamous.dungeons_gear.loot.LootTableRarity;
import com.infamous.dungeons_gear.loot.LootTableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.infamous.dungeons_gear.loot.LootTableRarity.*;
import static com.infamous.dungeons_gear.loot.LootTableType.*;

public class LootTablesConfigHelper {
    private final Map<LootTableType, List<String>> byType = new HashMap<>();
    private final Map<LootTableRarity, List<String>> byRarity = new HashMap<>();

    public void init(){
        vanilla();
        repurposedStructures();
        betterStrongholds();
        betterDungeons();
        dungeonCrawl();
        iceAndFire();
        towersOfTheWild();
        valhelsiaStructures();
        dungeonsPlus();
    }

    private void addLootTable(String s, LootTableRarity rarity, LootTableType type) {
        byType.computeIfAbsent(type, k -> new ArrayList<>()).add(s);
        byRarity.computeIfAbsent(rarity, k -> new ArrayList<>()).add(s);
    }

    public List<String> getLootTable(LootTableType type){
        return byType.get(type);
    }

    public List<String> getLootTable(LootTableRarity rarity){
        return byRarity.get(rarity);
    }

    private void vanilla() {
        addLootTable("minecraft:chests/abandoned_mineshaft", COMMON, BASIC);
        addLootTable("minecraft:chests/bastion", OBSIDIAN, NETHER);
        addLootTable("minecraft:chests/buried_treasure", OBSIDIAN, OCEAN);
        addLootTable("minecraft:chests/desert_pyramid", FANCY, DESERT);
        addLootTable("minecraft:chests/end_city_treasure", OBSIDIAN, END);
        addLootTable("minecraft:chests/igloo_chest", FANCY, COLD);
        addLootTable("minecraft:chests/jungle_temple", FANCY, JUNGLE);
        addLootTable("minecraft:chests/nether_bridge", FANCY, NETHER);
        addLootTable("minecraft:chests/pillager_outpost", FANCY, BASIC);
        addLootTable("minecraft:chests/ruined_portal", COMMON, NETHER);
        addLootTable("minecraft:chests/shipwreck", COMMON, OCEAN);
        addLootTable("minecraft:chests/simple_dungeon", FANCY, BASIC);
        addLootTable("minecraft:chests/stronghold", OBSIDIAN, BASIC);
        addLootTable("minecraft:chests/underwater_ruin", FANCY, OCEAN);
        addLootTable("minecraft:chests/woodland_mansion", OBSIDIAN, BASIC);
        addLootTable("minecraft:chests/village/village_armorer", COMMON, BASIC);
        addLootTable("minecraft:chests/village/village_tannery", COMMON, BASIC);
        addLootTable("minecraft:chests/village/village_temple", COMMON, BASIC);
        addLootTable("minecraft:chests/village/village_weaponsmith", COMMON, BASIC);
    }

    private void repurposedStructures() {
        addLootTable("repurposed_structures:chests/mineshaft/birch", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/crimson", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/desert", COMMON, DESERT);
        addLootTable("repurposed_structures:chests/mineshaft/end", COMMON, END);
        addLootTable("repurposed_structures:chests/mineshaft/icy", COMMON, COLD);
        addLootTable("repurposed_structures:chests/mineshaft/jungle", COMMON, JUNGLE);
        addLootTable("repurposed_structures:chests/mineshaft/nether", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/mineshaft/ocean", COMMON, OCEAN);
        addLootTable("repurposed_structures:chests/mineshaft/savanna", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/stone", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/dark_forest", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/swamp", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/taiga", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mineshaft/warped", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/bastions", OBSIDIAN, BASIC);
        addLootTable("repurposed_structures:chests/cities/nether", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/dungeon/badlands", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/dungeon/dark_forest", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/dungeon/desert", FANCY, DESERT);
        addLootTable("repurposed_structures:chests/dungeon/end", FANCY, END);
        addLootTable("repurposed_structures:chests/dungeon/jungle", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/dungeon/mushroom", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/dungeon/nether", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/dungeon/ocean", FANCY, OCEAN);
        addLootTable("repurposed_structures:chests/dungeon/snow", FANCY, COLD);
        addLootTable("repurposed_structures:chests/dungeon/swamp", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/fortress/jungle", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/fortress/jungle", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/fortress/jungle", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/igloos/grassy_chest", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/igloos/stone_chest", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/mansion/birch", OBSIDIAN, BASIC);
        addLootTable("repurposed_structures:chests/mansion/desert", OBSIDIAN, DESERT);
        addLootTable("repurposed_structures:chests/mansion/jungle", OBSIDIAN, JUNGLE);
        addLootTable("repurposed_structures:chests/mansion/oak", OBSIDIAN, BASIC);
        addLootTable("repurposed_structures:chests/mansion/savanna", OBSIDIAN, BASIC);
        addLootTable("repurposed_structures:chests/mansion/snowy", OBSIDIAN, COLD);
        addLootTable("repurposed_structures:chests/mansion/taiga", OBSIDIAN, BASIC);
        addLootTable("repurposed_structures:chests/outpost/badlands_chest", FANCY, DESERT);
        addLootTable("repurposed_structures:chests/outpost/birch_chest", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/outpost/crimson_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/outpost/desert_chest", FANCY, DESERT);
        addLootTable("repurposed_structures:chests/outpost/giant_tree_taiga_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/outpost/icy_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/outpost/jungle_chest", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/outpost/nether_brick_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/outpost/oak_chest", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/outpost/snowy_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/outpost/taiga_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/outpost/warped_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/outpost/end_shulker_box", FANCY, END);
        addLootTable("repurposed_structures:chests/pyramid/badlands_chest", FANCY, DESERT);
        addLootTable("repurposed_structures:chests/pyramid/nether_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/pyramid/snowy_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/pyramid/icy_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/pyramid/jungle_chest", FANCY, JUNGLE);
        addLootTable("repurposed_structures:chests/pyramid/mushroom_chest", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/pyramid/ocean_chest", FANCY, OCEAN);
        addLootTable("repurposed_structures:chests/pyramid/giant_tree_taiga_chest", FANCY, COLD);
        addLootTable("repurposed_structures:chests/pyramid/flower_forest_chest", FANCY, BASIC);
        addLootTable("repurposed_structures:chests/pyramid/end_chest", FANCY, END);
        addLootTable("repurposed_structures:chests/ruin/nether", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/ruin/land_hot/large", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/ruin/land_hot/small", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/ruin/land_warm/large", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/ruin/land_warm/small", COMMON, BASIC);
        addLootTable("repurposed_structures:chests/ruined_portal/large_portal_chest", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/ruined_portal/small_portal_chest", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/crimson/", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/crimson/", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/crimson/", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/end/map_chest", FANCY, END);
        addLootTable("repurposed_structures:chests/shipwreck/end/supply_chest", FANCY, END);
        addLootTable("repurposed_structures:chests/shipwreck/end/treasure_chest", FANCY, END);
        addLootTable("repurposed_structures:chests/shipwreck/warped/map_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/warped/supply_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/warped/treasure_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/shipwreck/nether_bricks/treasure_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:shulker_boxes/stronghold/end_storage_room", OBSIDIAN, END);
        addLootTable("repurposed_structures:shulker_boxes/stronghold/end_hallway", OBSIDIAN, END);
        addLootTable("repurposed_structures:shulker_boxes/stronghold/end_library", OBSIDIAN, END);
        addLootTable("repurposed_structures:chests/stronghold/nether_storage_room", OBSIDIAN, NETHER);
        addLootTable("repurposed_structures:chests/stronghold/nether_hallway", OBSIDIAN, NETHER);
        addLootTable("repurposed_structures:chests/stronghold/nether_library", OBSIDIAN, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_basalt_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_crimson_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_soul_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_warped_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_warped_trapped_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/temple/nether_wasteland_chest", FANCY, NETHER);
        addLootTable("repurposed_structures:chests/village/village_crimson_tanner", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/village/village_crimson_weaponsmith", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/village/village_warped_tanner", COMMON, NETHER);
        addLootTable("repurposed_structures:chests/village/village_warped_weaponsmith", COMMON, NETHER);
    }

    private void betterStrongholds() {
        addLootTable("betterstrongholds:chests/armoury", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/common", FANCY, BASIC);
        addLootTable("betterstrongholds:chests/crypt", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/mess", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/prison_lg", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/trap", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/treasure", OBSIDIAN, BASIC);
        addLootTable("betterstrongholds:chests/end/armoury", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/end/common", FANCY, END);
        addLootTable("betterstrongholds:chests/end/crypt", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/end/mess", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/end/prison_lg", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/end/trap", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/end/treasure", OBSIDIAN, END);
        addLootTable("betterstrongholds:chests/nether/common", FANCY, NETHER);
        addLootTable("betterstrongholds:chests/nether/mess", OBSIDIAN, NETHER);
        addLootTable("betterstrongholds:chests/nether/armoury", OBSIDIAN, NETHER);
        addLootTable("betterstrongholds:chests/nether/crypt", OBSIDIAN, NETHER);
        addLootTable("betterstrongholds:chests/nether/prison_lg", OBSIDIAN, NETHER);
        addLootTable("betterstrongholds:chests/nether/trap", OBSIDIAN, NETHER);
        addLootTable("betterstrongholds:chests/nether/treasure", OBSIDIAN, NETHER);
    }

    private void betterDungeons() {
        addLootTable("betterdungeons:skeleton_dungeon/chests/common", FANCY, BASIC);
        addLootTable("betterdungeons:skeleton_dungeon/chests/middle", FANCY, BASIC);
        addLootTable("betterdungeons:small_dungeon/chests/loot_piles", COMMON, BASIC);
        addLootTable("betterdungeons:skeleton_dungeon/chests/common", FANCY, NETHER);
        addLootTable("betterdungeons:spider_dungeon/chests/egg_room", FANCY, BASIC);
        addLootTable("betterdungeons:zombie_dungeon/chests/common", FANCY, BASIC);
        addLootTable("betterdungeons:zombie_dungeon/chests/special", FANCY, BASIC);
        addLootTable("betterdungeons:zombie_dungeon/chests/tombstone", FANCY, BASIC);
    }

    private void dungeonCrawl() {
        addLootTable("dungeoncrawl:chests/stage_1", COMMON, BASIC);
        addLootTable("dungeoncrawl:chests/stage_2", COMMON, BASIC);
        addLootTable("dungeoncrawl:chests/stage_3", FANCY, BASIC);
        addLootTable("dungeoncrawl:chests/stage_4", FANCY, BASIC);
        addLootTable("dungeoncrawl:chests/stage_5", OBSIDIAN, BASIC);
        addLootTable("dungeoncrawl:chests/treasure", OBSIDIAN, BASIC);
        addLootTable("dungeoncrawl:chests/forge", FANCY, BASIC);
    }

    private void iceAndFire() {
        addLootTable("iceandfire:chest/lightning_dragon_roost", COMMON, BASIC);
        addLootTable("iceandfire:chest/fire_dragon_female_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/ice_dragon_male_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/ice_dragon_roost", COMMON, BASIC);
        addLootTable("iceandfire:chest/lightning_dragon_female_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/fire_dragon_male_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/myrmex_loot_chest", COMMON, BASIC);
        addLootTable("iceandfire:chest/ice_dragon_female_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/fire_dragon_roost", COMMON, BASIC);
        addLootTable("iceandfire:chest/lightning_dragon_male_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/mausoleum_chest", OBSIDIAN, BASIC);
        addLootTable("iceandfire:chest/cyclops_cave", COMMON, BASIC);
        addLootTable("iceandfire:chest/graveyard", COMMON, BASIC);
        addLootTable("iceandfire:chest/hydra_cave", COMMON, BASIC);
    }

    private void towersOfTheWild() {
        addLootTable("totw_reworked:ocean_tower_chest", COMMON, BASIC);
        addLootTable("totw_reworked:tower_chest", COMMON, BASIC);
        addLootTable("towers_of_the_wild:chests/tower/ocean/ocean_tower_chest", COMMON, BASIC);
        addLootTable("towers_of_the_wild:chests/tower/regular/tower_chest", COMMON, BASIC);
    }

    private void valhelsiaStructures() {
        addLootTable("valhelsia_structures:chests/castle", COMMON, BASIC);
        addLootTable("valhelsia_structures:chests/castle_ruin", COMMON, BASIC);
        addLootTable("valhelsia_structures:chests/desert_house", FANCY, DESERT);
        addLootTable("valhelsia_structures:chests/forge", FANCY, BASIC);
        addLootTable("valhelsia_structures:chests/player_house", COMMON, BASIC);
        addLootTable("valhelsia_structures:chests/spawner_dungeon", FANCY, BASIC);
        addLootTable("valhelsia_structures:chests/treasure", COMMON, BASIC);
        addLootTable("valhelsia_structures:chests/witch_hut", COMMON, BASIC);
    }

    private void dungeonsPlus() {
        addLootTable("dungeons_plus:chests/bigger_dungeon/husk", COMMON, DESERT);
        addLootTable("dungeons_plus:chests/bigger_dungeon/stray", COMMON, COLD);
        addLootTable("dungeons_plus:chests/leviathan/common", COMMON, DESERT);
        addLootTable("dungeons_plus:chests/snowy_temple/common", COMMON, COLD);
        addLootTable("dungeons_plus:chests/soul_prison/common", FANCY, BASIC);
        addLootTable("dungeons_plus:chests/tower/vex", FANCY, BASIC);
        addLootTable("dungeons_plus:chests/vanilla_dungeon", COMMON, BASIC);
        addLootTable("dungeons_plus:chests/warped_garden/common", COMMON, NETHER);
    }
}
