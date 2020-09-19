package com.infamous.dungeons_gear.loot;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.items.ArtifactList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.infamous.dungeons_gear.items.ArtifactList.*;
import static com.infamous.dungeons_gear.items.WeaponList.*;
import static com.infamous.dungeons_gear.items.ArmorList.*;
import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class LootHandler {

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event){
        if(!DungeonsGearConfig.COMMON.ENABLE_VILLAGER_TRADES.get()) return;

        List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ITrade> rareTrades = event.getRareTrades();

        moveTradesToDifferentGroup(rareTrades, genericTrades);

        for(Item item : ArtifactList.artifactMap.keySet()){
            ResourceLocation resourceLocation = ArtifactList.artifactMap.get(item);
            Item artifact = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack artifactStack = new ItemStack(artifact);
            BasicTrade trade = new BasicTrade(DungeonsGearConfig.COMMON.ARTIFACT_VALUE.get(), artifactStack, 3, 30);
            rareTrades.add(trade);
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event){
        if(!DungeonsGearConfig.COMMON.ENABLE_VILLAGER_TRADES.get()) return;

        if(event.getType() == VillagerProfession.WEAPONSMITH){
            Int2ObjectMap<List<VillagerTrades.ITrade>> weaponsmithTrades = event.getTrades();

            // Moving the Level 4 "Diamond for Emerald" Trade down to Level 3
            VillagerTrades.ITrade diamondForEmeraldTrade = weaponsmithTrades.get(4).get(0);
            weaponsmithTrades.get(3).add(diamondForEmeraldTrade);
            weaponsmithTrades.get(4).remove(0);

            addCommonAndUniqueTrades(weaponsmithTrades, commonWeaponMap, uniqueWeaponMap);
        }
        if(event.getType() == VillagerProfession.FLETCHER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> fletcherTrades = event.getTrades();

            // Move higher level stuff to lower trade levels
            VillagerTrades.ITrade emeraldForFeatherTrade = fletcherTrades.get(4).get(0);
            VillagerTrades.ITrade emeraldForTripwireHookTrade = fletcherTrades.get(5).get(0);
            VillagerTrades.ITrade emeraldAndArrowForTippedArrowTrade = fletcherTrades.get(5).get(2);
            fletcherTrades.get(2).add(emeraldForFeatherTrade);
            fletcherTrades.get(3).add(emeraldForTripwireHookTrade);
            fletcherTrades.get(3).add(emeraldAndArrowForTippedArrowTrade);
            fletcherTrades.get(4).remove(0);
            fletcherTrades.get(5).remove(0);
            fletcherTrades.get(5).remove(1); // since the 1st trade was already removed, the 3rd trade is now the second

            //moveTradesToDifferentGroup(fletcherTrades.get(4), fletcherTrades.get(2));
            //moveTradesToDifferentGroup(fletcherTrades.get(5),  fletcherTrades.get(3));

            addCommonAndUniqueTrades(fletcherTrades, commonRangedWeaponMap, uniqueRangedWeaponMap);
        }

        if(event.getType() == VillagerProfession.ARMORER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> armorerTrades = event.getTrades();
            addCommonAndUniqueTrades(armorerTrades, commonMetalArmorMap, uniqueMetalArmorMap);
        }

        if(event.getType() == VillagerProfession.LEATHERWORKER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> leatherworkerTrades = event.getTrades();

            // Move higher level stuff to lower trade levels
            moveTradesToDifferentGroup(leatherworkerTrades.get(4), leatherworkerTrades.get(2));
            moveTradesToDifferentGroup(leatherworkerTrades.get(5),  leatherworkerTrades.get(3));

            addCommonAndUniqueTrades(leatherworkerTrades, commonLeatherArmorMap, uniqueLeatherArmorMap);
        }
    }

    private static void addCommonAndUniqueTrades(Int2ObjectMap<List<VillagerTrades.ITrade>> villagerTrades, Map<Item, ResourceLocation> commonMap, Map<Item, ResourceLocation> uniqueMap) {
        for(Item item : commonMap.keySet()){
            ResourceLocation resourceLocation = commonMap.get(item);
            Item weapon = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack weaponStack = new ItemStack(weapon);
            TradeUtils.EnchantedItemForEmeraldsTrade trade = new TradeUtils.EnchantedItemForEmeraldsTrade(weapon, DungeonsGearConfig.COMMON.COMMON_ITEM_VALUE.get(), 3, 15,0.2F);

            villagerTrades.get(4).add(trade);
        }
        for(Item item : uniqueMap.keySet()){
            ResourceLocation resourceLocation = uniqueMap.get(item);
            Item weapon = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack weaponStack = new ItemStack(weapon);
            TradeUtils.EnchantedItemForEmeraldsTrade trade = new TradeUtils.EnchantedItemForEmeraldsTrade(weapon, DungeonsGearConfig.COMMON.UNIQUE_ITEM_VALUE.get(), 3, 30,0.2F);

            villagerTrades.get(5).add(trade);
        }
    }

    private static void moveTradesToDifferentGroup(List<VillagerTrades.ITrade> oldTradesGroup, List<VillagerTrades.ITrade> newTradesGroup) {
        Iterator<VillagerTrades.ITrade> it = oldTradesGroup.iterator();
        while (it.hasNext()) {
            VillagerTrades.ITrade currentTrade = it.next();
            if (currentTrade != null) {
                newTradesGroup.add(currentTrade);
                it.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event){
        if(!DungeonsGearConfig.COMMON.ENABLE_DUNGEONS_GEAR_LOOT.get()) return;
        String eventPath = event.getName().toString();

        // SUPER RARE
        DungeonsGearConfig.COMMON.SUPER_RARE_LOOT_TABLES.get().forEach((path) ->{
            if(eventPath.contains(path) && !DungeonsGearConfig.COMMON.SUPER_RARE_LOOT_TABLES_BLACKLIST.get().contains(path)){
                LootTable table = event.getTable();
                addLootTable(table, "SUPER RARE");
            }
        });

        // RARE
        DungeonsGearConfig.COMMON.RARE_LOOT_TABLES.get().forEach((path) ->{
            if(eventPath.contains(path) && !DungeonsGearConfig.COMMON.RARE_LOOT_TABLES_BLACKLIST.get().contains(path)){
                LootTable table = event.getTable();
                addLootTable(table, "RARE");
            }
        });

        // UNCOMMON
        DungeonsGearConfig.COMMON.UNCOMMON_LOOT_TABLES.get().forEach((path) ->{
            if(eventPath.contains(path) && !DungeonsGearConfig.COMMON.UNCOMMON_LOOT_TABLES_BLACKLIST.get().contains(path)){
                LootTable table = event.getTable();
                addLootTable(table, "UNCOMMON");
            }
        });

        //COMMON
        DungeonsGearConfig.COMMON.COMMON_LOOT_TABLES.get().forEach((path) ->{
            if(eventPath.contains(path) && !DungeonsGearConfig.COMMON.COMMON_LOOT_TABLES_BLACKLIST.get().contains(path)){
                LootTable table = event.getTable();
                addLootTable(table, "COMMON");
            }
        });

        if(event.getName().toString().contains("minecraft:chests/village/village_weaponsmith")){
            //DungeonsGear.LOGGER.info("Handled the Weaponsmith's Chest loot table!");
            LootTable table = event.getTable();
            addCommonMeleeWeaponLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:chests/village/village_fletcher")){
            //DungeonsGear.LOGGER.info("Handled the Fletcher's Chest loot table!");
            LootTable table = event.getTable();
            addCommonRangedWeaponLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:chests/village/village_armorer")){
            //DungeonsGear.LOGGER.info("Handled the Weaponsmith's Chest loot table!");
            LootTable table = event.getTable();
            addCommonMetalArmorLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:chests/village/village_leatherworker")){
            //DungeonsGear.LOGGER.info("Handled the Fletcher's Chest loot table!");
            LootTable table = event.getTable();
            addCommonLeatherArmorLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:gameplay/hero_of_the_village/weaponsmith_gift")){
            //DungeonsGear.LOGGER.info("Handled the Weaponsmith's Gift loot table!");
            LootTable table = event.getTable();
            addCommonMeleeWeaponLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:gameplay/hero_of_the_village/fletcher_gift")){
            //DungeonsGear.LOGGER.info("Handled the Fletcher's Gift loot table!");
            LootTable table = event.getTable();
            addCommonRangedWeaponLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:gameplay/hero_of_the_village/armorer_gift")){
            //DungeonsGear.LOGGER.info("Handled the Armorer's Gift loot table!");
            LootTable table = event.getTable();
            addCommonMetalArmorLootTable(table);

        }
        if(event.getName().toString().contains("minecraft:gameplay/hero_of_the_village/leatherworker_gift")){
            //DungeonsGear.LOGGER.info("Handled the Leatherworker's Gift loot table!");
            LootTable table = event.getTable();
            addCommonLeatherArmorLootTable(table);

        }
    }

    private static void addCommonMeleeWeaponLootTable(LootTable table){
        Collection<ResourceLocation> commonWeapons = commonWeaponMap.values();
        Collection<ResourceLocation> uniqueWeapons = uniqueWeaponMap.values();

        LootUtils.myAddItemsToTable(table, commonWeapons, 1, DungeonsGearConfig.COMMON.COMMON_WEAPON_COMMON_LOOT.get().floatValue(), "common_weapons");
        LootUtils.myAddItemsToTable(table, uniqueWeapons, 1, DungeonsGearConfig.COMMON.UNIQUE_WEAPON_COMMON_LOOT.get().floatValue(), "unique_weapons");
    }
    private static void addCommonRangedWeaponLootTable(LootTable table){
        Collection<ResourceLocation> commonRangedWeapons = commonRangedWeaponMap.values();
        Collection<ResourceLocation> uniqueRangedWeapons = uniqueRangedWeaponMap.values();

        LootUtils.myAddItemsToTable(table, commonRangedWeapons,  1, DungeonsGearConfig.COMMON.COMMON_WEAPON_COMMON_LOOT.get().floatValue(), "common_ranged_weapons");
        LootUtils.myAddItemsToTable(table, uniqueRangedWeapons,  1, DungeonsGearConfig.COMMON.UNIQUE_WEAPON_COMMON_LOOT.get().floatValue(), "unique_ranged_weapons");
    }

    private static void addCommonMetalArmorLootTable(LootTable table){
        Collection<ResourceLocation> commonWeapons = commonMetalArmorMap.values();
        Collection<ResourceLocation> uniqueWeapons = uniqueMetalArmorMap.values();

        LootUtils.myAddItemsToTable(table, commonWeapons,  1, DungeonsGearConfig.COMMON.COMMON_ARMOR_COMMON_LOOT.get().floatValue(), "common_metal_armor");
        LootUtils.myAddItemsToTable(table, uniqueWeapons,  1, DungeonsGearConfig.COMMON.UNIQUE_ARMOR_COMMON_LOOT.get().floatValue(), "unique_metal_armor");
    }
    private static void addCommonLeatherArmorLootTable(LootTable table){
        Collection<ResourceLocation> commonRangedWeapons = commonLeatherArmorMap.values();
        Collection<ResourceLocation> uniqueRangedWeapons = uniqueLeatherArmorMap.values();

        LootUtils.myAddItemsToTable(table, commonRangedWeapons,  1, DungeonsGearConfig.COMMON.COMMON_ARMOR_COMMON_LOOT.get().floatValue(), "common_leather_armor");
        LootUtils.myAddItemsToTable(table, uniqueRangedWeapons,  1, DungeonsGearConfig.COMMON.UNIQUE_ARMOR_COMMON_LOOT.get().floatValue(), "unique_leather_armor");
    }

    private static void addLootTable(LootTable table, String rarity){
        Collection<ResourceLocation> commonWeaponCollectionCombined = getCommonWeaponCollection();
        Collection<ResourceLocation> uniqueWeaponCollectionCombined = getUniqueWeaponCollection();
        Collection<ResourceLocation> commonArmorCollectionCombined = getCommonArmorCollection();
        Collection<ResourceLocation> uniqueArmorCollectionCombined = getUniqueArmorCollection();

        Collection<ResourceLocation> artifacts = artifactMap.values();

        float commonWeaponChance;
        float uniqueWeaponChance;
        float commonArmorChance;
        float uniqueArmorChance;
        float artifactChance;

        switch(rarity.toUpperCase()){
            case "COMMON":
                commonWeaponChance = DungeonsGearConfig.COMMON.COMMON_WEAPON_COMMON_LOOT.get().floatValue();
                uniqueWeaponChance = DungeonsGearConfig.COMMON.UNIQUE_WEAPON_COMMON_LOOT.get().floatValue();
                commonArmorChance = DungeonsGearConfig.COMMON.COMMON_ARMOR_COMMON_LOOT.get().floatValue();
                uniqueArmorChance = DungeonsGearConfig.COMMON.UNIQUE_ARMOR_COMMON_LOOT.get().floatValue();
                artifactChance = DungeonsGearConfig.COMMON.ARTIFACT_COMMON_LOOT.get().floatValue();
                break;
            case "UNCOMMON":
                commonWeaponChance = DungeonsGearConfig.COMMON.COMMON_WEAPON_UNCOMMON_LOOT.get().floatValue();
                uniqueWeaponChance = DungeonsGearConfig.COMMON.UNIQUE_WEAPON_UNCOMMON_LOOT.get().floatValue();
                commonArmorChance = DungeonsGearConfig.COMMON.COMMON_ARMOR_UNCOMMON_LOOT.get().floatValue();
                uniqueArmorChance = DungeonsGearConfig.COMMON.UNIQUE_ARMOR_UNCOMMON_LOOT.get().floatValue();
                artifactChance = DungeonsGearConfig.COMMON.ARTIFACT_UNCOMMON_LOOT.get().floatValue();
                break;
            case "RARE":
                commonWeaponChance = DungeonsGearConfig.COMMON.COMMON_WEAPON_RARE_LOOT.get().floatValue();
                uniqueWeaponChance = DungeonsGearConfig.COMMON.UNIQUE_WEAPON_RARE_LOOT.get().floatValue();
                commonArmorChance = DungeonsGearConfig.COMMON.COMMON_ARMOR_RARE_LOOT.get().floatValue();
                uniqueArmorChance = DungeonsGearConfig.COMMON.UNIQUE_ARMOR_RARE_LOOT.get().floatValue();
                artifactChance = DungeonsGearConfig.COMMON.ARTIFACT_RARE_LOOT.get().floatValue();
                break;
            case "SUPER RARE":
                commonWeaponChance = DungeonsGearConfig.COMMON.COMMON_WEAPON_SUPER_RARE_LOOT.get().floatValue();
                uniqueWeaponChance = DungeonsGearConfig.COMMON.UNIQUE_WEAPON_SUPER_RARE_LOOT.get().floatValue();
                commonArmorChance = DungeonsGearConfig.COMMON.COMMON_ARMOR_SUPER_RARE_LOOT.get().floatValue();
                uniqueArmorChance = DungeonsGearConfig.COMMON.UNIQUE_ARMOR_SUPER_RARE_LOOT.get().floatValue();
                artifactChance = DungeonsGearConfig.COMMON.ARTIFACT_SUPER_RARE_LOOT.get().floatValue();
                break;
            default:
                commonWeaponChance = DungeonsGearConfig.COMMON.COMMON_WEAPON_COMMON_LOOT.get().floatValue();
                uniqueWeaponChance = DungeonsGearConfig.COMMON.UNIQUE_WEAPON_COMMON_LOOT.get().floatValue();
                commonArmorChance = DungeonsGearConfig.COMMON.COMMON_ARMOR_COMMON_LOOT.get().floatValue();
                uniqueArmorChance = DungeonsGearConfig.COMMON.UNIQUE_ARMOR_COMMON_LOOT.get().floatValue();
                artifactChance = DungeonsGearConfig.COMMON.ARTIFACT_COMMON_LOOT.get().floatValue();
                break;
        }

        LootUtils.myAddItemsToTable(table, commonWeaponCollectionCombined,  1, commonWeaponChance, "common_weapons");
        LootUtils.myAddItemsToTable(table, uniqueWeaponCollectionCombined,  1, uniqueWeaponChance, "unique_weapons");
        LootUtils.myAddItemsToTable(table, commonArmorCollectionCombined,  1, commonArmorChance, "common_armor");
        LootUtils.myAddItemsToTable(table, uniqueArmorCollectionCombined,  1, uniqueArmorChance, "unique_armor");
        LootUtils.myAddItemsToTable(table, artifacts,  1, artifactChance, "artifacts");
    }

    private static Collection<ResourceLocation> getUniqueArmorCollection() {
        Collection<ResourceLocation> uniqueLeatherArmor = uniqueLeatherArmorMap.values();
        Collection<ResourceLocation> uniqueMetalArmor = uniqueMetalArmorMap.values();
        Iterable<ResourceLocation> combinedUniqueArmor = Iterables.unmodifiableIterable(
                Iterables.concat(uniqueMetalArmor, uniqueLeatherArmor));
        return Lists.newArrayList(combinedUniqueArmor);
    }

    private static Collection<ResourceLocation> getCommonArmorCollection() {
        Collection<ResourceLocation> commonLeatherArmor = commonLeatherArmorMap.values();
        Collection<ResourceLocation> commonMetalArmor = commonMetalArmorMap.values();
        Iterable<ResourceLocation> combinedCommonArmor = Iterables.unmodifiableIterable(
                Iterables.concat(commonMetalArmor, commonLeatherArmor));
        return Lists.newArrayList(combinedCommonArmor);
    }

    private static Collection<ResourceLocation> getUniqueWeaponCollection() {
        Collection<ResourceLocation> uniqueWeapons = uniqueWeaponMap.values();
        Collection<ResourceLocation> uniqueRangedWeapons = uniqueRangedWeaponMap.values();
        Iterable<ResourceLocation> combinedUniqueWeapons = Iterables.unmodifiableIterable(
                Iterables.concat(uniqueWeapons, uniqueRangedWeapons));
        return Lists.newArrayList(combinedUniqueWeapons);
    }
    private static Collection<ResourceLocation> getCommonWeaponCollection() {
        Collection<ResourceLocation> commonWeapons = commonWeaponMap.values();
        Collection<ResourceLocation> commonRangedWeapons = commonRangedWeaponMap.values();
        Iterable<ResourceLocation> combinedCommonWeapons = Iterables.unmodifiableIterable(
                Iterables.concat(commonWeapons, commonRangedWeapons));
        return Lists.newArrayList(combinedCommonWeapons);
    }

    @SubscribeEvent
    public static void onSalvageItem(PlayerInteractEvent.EntityInteract event){
        if(!DungeonsGearConfig.COMMON.ENABLE_SALVAGING.get()) return;
        Entity entity = event.getTarget();
        PlayerEntity playerEntity = event.getPlayer();
        if(entity instanceof VillagerEntity){
            VillagerEntity villagerEntity = (VillagerEntity) entity;
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.WEAPONSMITH){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(commonWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(uniqueWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.FLETCHER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(commonRangedWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(uniqueRangedWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.ARMORER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(commonMetalArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(uniqueMetalArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(commonLeatherArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(uniqueLeatherArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
        }
        if(entity instanceof WanderingTraderEntity){
            WanderingTraderEntity wanderingTraderEntity = (WanderingTraderEntity) entity;
            if(playerEntity.isSneaking()){
                ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                if(artifactMap.containsKey(interactStack.getItem())){
                    handleSalvageTrade(playerEntity, wanderingTraderEntity, interactStack, "ARTIFACT");
                }
            }
        }
    }

    private static void handleSalvageTrade(PlayerEntity playerEntity, AbstractVillagerEntity abstractVillagerEntity, ItemStack interactStack, String itemType) {
        float maxDamage = interactStack.getMaxDamage();
        DungeonsGear.LOGGER.info("Max damage: " + maxDamage);
        float currentDamage = maxDamage - interactStack.getDamage();
        DungeonsGear.LOGGER.info("Current damage: " + currentDamage);

        int itemValue;
        switch(itemType.toUpperCase()){
            case "COMMON":
                itemValue = DungeonsGearConfig.COMMON.COMMON_ITEM_VALUE.get();
                break;
            case "UNIQUE":
                itemValue  = DungeonsGearConfig.COMMON.UNIQUE_ITEM_VALUE.get();
                break;
            case "ARTIFACT":
                itemValue = DungeonsGearConfig.COMMON.ARTIFACT_VALUE.get();
                break;
            default:
                itemValue = DungeonsGearConfig.COMMON.COMMON_ITEM_VALUE.get();
        }

        int emeraldReward = Math.round(itemValue * 0.375F);
        float durabilityModifier = currentDamage / maxDamage;
        emeraldReward = Math.round(emeraldReward * durabilityModifier);

        if(emeraldReward > 0){
            interactStack.shrink(1);

            if(abstractVillagerEntity instanceof VillagerEntity){
                VillagerEntity villagerEntity = (VillagerEntity)abstractVillagerEntity;
                villagerEntity.world.playSound((PlayerEntity) null, abstractVillagerEntity.getPosX(), abstractVillagerEntity.getPosY(), abstractVillagerEntity.getPosZ(), SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.PLAYERS, 64.0F, 1.0F);
                villagerEntity.updateReputation(IReputationType.TRADE, playerEntity);
            }
            else if(abstractVillagerEntity instanceof WanderingTraderEntity){
                WanderingTraderEntity wanderingTraderEntity = (WanderingTraderEntity)abstractVillagerEntity;
                wanderingTraderEntity.world.playSound((PlayerEntity) null, wanderingTraderEntity.getPosX(), wanderingTraderEntity.getPosY(), wanderingTraderEntity.getPosZ(), SoundEvents.ENTITY_WANDERING_TRADER_YES, SoundCategory.PLAYERS, 64.0F, 1.0F);
            }
            ItemStack emeraldStack = new ItemStack(Items.EMERALD, emeraldReward);
            if(!playerEntity.addItemStackToInventory(emeraldStack)){
                World world = playerEntity.getEntityWorld();
                ItemEntity emeraldStackEntity = new ItemEntity(world, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), emeraldStack);
                world.addEntity(emeraldStackEntity);
            }
            playerEntity.giveExperiencePoints(emeraldReward);
        }
    }
}
