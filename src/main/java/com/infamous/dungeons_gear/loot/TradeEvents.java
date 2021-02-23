package com.infamous.dungeons_gear.loot;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Predicate;

import static net.minecraft.item.Items.*;


@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class TradeEvents {

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event){
        if(!DungeonsGearConfig.ENABLE_VILLAGER_TRADES.get()) return;

        List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ITrade> rareTrades = event.getRareTrades();

        moveTradesToDifferentGroup(rareTrades, genericTrades);

        for(Item item : DeferredItemInit.artifactMap.keySet()){
            ResourceLocation resourceLocation = DeferredItemInit.artifactMap.get(item);
            Item artifact = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack artifactStack = new ItemStack(artifact);
            BasicTrade trade = new BasicTrade(DungeonsGearConfig.ARTIFACT_VALUE.get(), artifactStack, 3, 30);
            rareTrades.add(trade);
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event){
        if(!DungeonsGearConfig.ENABLE_VILLAGER_TRADES.get()) return;

        if(event.getType() == VillagerProfession.WEAPONSMITH){
            Int2ObjectMap<List<VillagerTrades.ITrade>> weaponsmithTrades = event.getTrades();

            // Moving the Level 4 "Diamond for Emerald" Trade down to Level 3
            moveTrade(weaponsmithTrades, 4, 3, emeraldForItemFilter(DIAMOND));

            addCommonAndUniqueTrades(weaponsmithTrades, DeferredItemInit.commonWeaponMap, DeferredItemInit.uniqueWeaponMap);
        }
        if(event.getType() == VillagerProfession.FLETCHER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> fletcherTrades = event.getTrades();

            // Move higher level stuff to lower trade levels
            moveTrade(fletcherTrades, 4, 2, emeraldForItemFilter(FEATHER));
            moveTrade(fletcherTrades, 5, 3, emeraldForItemFilter(TRIPWIRE_HOOK));
            moveTrade(fletcherTrades, 5, 3, itemWithPotionForEmeraldsAndItemsFilter(TIPPED_ARROW));

            //moveTradesToDifferentGroup(fletcherTrades.get(4), fletcherTrades.get(2));
            //moveTradesToDifferentGroup(fletcherTrades.get(5),  fletcherTrades.get(3));

            addCommonAndUniqueTrades(fletcherTrades, DeferredItemInit.commonRangedWeaponMap, DeferredItemInit.uniqueRangedWeaponMap);
        }

        if(event.getType() == VillagerProfession.ARMORER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> armorerTrades = event.getTrades();
            addCommonAndUniqueTrades(armorerTrades, DeferredItemInit.commonMetalArmorMap, DeferredItemInit.uniqueMetalArmorMap);
        }

        if(event.getType() == VillagerProfession.LEATHERWORKER){
            Int2ObjectMap<List<VillagerTrades.ITrade>> leatherworkerTrades = event.getTrades();

            // Move higher level stuff to lower trade levels
            moveTradesToDifferentGroup(leatherworkerTrades.get(4), leatherworkerTrades.get(2));
            moveTradesToDifferentGroup(leatherworkerTrades.get(5),  leatherworkerTrades.get(3));

            addCommonAndUniqueTrades(leatherworkerTrades, DeferredItemInit.commonLeatherArmorMap, DeferredItemInit.uniqueLeatherArmorMap);
        }
    }

    private static void moveTrade(Int2ObjectMap<List<VillagerTrades.ITrade>> trades, int source, int target, Predicate<VillagerTrades.ITrade> filter){
        Optional<VillagerTrades.ITrade> emeraldForFeatherTrade = getVillagerTrade(trades.get(source), filter);
        emeraldForFeatherTrade.ifPresent(iTrade -> {
            trades.get(target).add(iTrade);
            trades.get(source).removeIf(filter);
        });
    }

    private static Optional<VillagerTrades.ITrade> getVillagerTrade(List<VillagerTrades.ITrade> trades, Predicate< VillagerTrades.ITrade > filter){
        return trades.stream().filter(filter).findFirst();
    }

    private static Predicate<VillagerTrades.ITrade> emeraldForItemFilter(Item item){
        return (iTrade) -> (iTrade instanceof VillagerTrades.EmeraldForItemsTrade && ((VillagerTrades.EmeraldForItemsTrade) iTrade).tradeItem.equals(item));
    }

    private static Predicate<VillagerTrades.ITrade> itemsForEmeraldsFilter(Item item){
        return (iTrade) -> (iTrade instanceof VillagerTrades.ItemsForEmeraldsTrade && ((VillagerTrades.ItemsForEmeraldsTrade) iTrade).sellingItem.getItem().equals(item));
    }

    private static Predicate<VillagerTrades.ITrade> itemWithPotionForEmeraldsAndItemsFilter(Item item){
        return (iTrade) -> (iTrade instanceof VillagerTrades.ItemWithPotionForEmeraldsAndItemsTrade && ((VillagerTrades.ItemWithPotionForEmeraldsAndItemsTrade) iTrade).potionStack.getItem().equals(item));
    }

    private static void addCommonAndUniqueTrades(Int2ObjectMap<List<VillagerTrades.ITrade>> villagerTrades, Map<Item, ResourceLocation> commonMap, Map<Item, ResourceLocation> uniqueMap) {
        for(Item item : commonMap.keySet()){
            ResourceLocation resourceLocation = commonMap.get(item);
            Item weapon = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack weaponStack = new ItemStack(weapon);
            TradeUtils.EnchantedItemForEmeraldsTrade trade = new TradeUtils.EnchantedItemForEmeraldsTrade(weapon, DungeonsGearConfig.COMMON_ITEM_VALUE.get(), 3, 15,0.2F);

            villagerTrades.get(4).add(trade);
        }
        for(Item item : uniqueMap.keySet()){
            ResourceLocation resourceLocation = uniqueMap.get(item);
            Item weapon = ForgeRegistries.ITEMS.getValue(resourceLocation);
            ItemStack weaponStack = new ItemStack(weapon);
            TradeUtils.EnchantedItemForEmeraldsTrade trade = new TradeUtils.EnchantedItemForEmeraldsTrade(weapon, DungeonsGearConfig.UNIQUE_ITEM_VALUE.get(), 3, 30,0.2F);

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
    public static void onSalvageItem(PlayerInteractEvent.EntityInteract event){
        if(!DungeonsGearConfig.ENABLE_SALVAGING.get()) return;
        Entity entity = event.getTarget();
        PlayerEntity playerEntity = event.getPlayer();
        if(entity instanceof VillagerEntity){
            VillagerEntity villagerEntity = (VillagerEntity) entity;
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.WEAPONSMITH){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(DeferredItemInit.commonWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(DeferredItemInit.uniqueWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.FLETCHER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(DeferredItemInit.commonRangedWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(DeferredItemInit.uniqueRangedWeaponMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.ARMORER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(DeferredItemInit.commonMetalArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(DeferredItemInit.uniqueMetalArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
            if(villagerEntity.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER){
                if(playerEntity.isSneaking()){
                    ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                    if(DeferredItemInit.commonLeatherArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "COMMON");
                    }
                    else if(DeferredItemInit.uniqueLeatherArmorMap.containsKey(interactStack.getItem())){
                        handleSalvageTrade(playerEntity, villagerEntity, interactStack, "UNIQUE");
                    }
                }
            }
        }
        if(entity instanceof WanderingTraderEntity){
            WanderingTraderEntity wanderingTraderEntity = (WanderingTraderEntity) entity;
            if(playerEntity.isSneaking()){
                ItemStack interactStack = playerEntity.getHeldItem(event.getHand());
                if(DeferredItemInit.artifactMap.containsKey(interactStack.getItem())){
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
                itemValue = DungeonsGearConfig.COMMON_ITEM_VALUE.get();
                break;
            case "UNIQUE":
                itemValue  = DungeonsGearConfig.UNIQUE_ITEM_VALUE.get();
                break;
            case "ARTIFACT":
                itemValue = DungeonsGearConfig.ARTIFACT_VALUE.get();
                break;
            default:
                itemValue = DungeonsGearConfig.COMMON_ITEM_VALUE.get();
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
