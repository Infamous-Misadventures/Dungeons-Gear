package com.infamous.dungeons_gear.loot;

import com.google.gson.JsonObject;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;


public class GlobalLootModifier{

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class GlobalLootModifierRegistry {

        @SubscribeEvent
        public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
            event.getRegistry().register(
                    new CommonLootAdditions.Serializer().setRegistryName(new ResourceLocation(MODID,"common_loot_additions"))
                );
            event.getRegistry().register(
                    new UncommonLootAdditions.Serializer().setRegistryName(new ResourceLocation(MODID,"uncommon_loot_additions"))
            );
            event.getRegistry().register(
                    new RareLootAdditions.Serializer().setRegistryName(new ResourceLocation(MODID,"rare_loot_additions"))
            );
            event.getRegistry().register(
                    new SuperRareLootAdditions.Serializer().setRegistryName(new ResourceLocation(MODID,"super_rare_loot_additions"))
            );
        }
    }

    public static class CommonLootAdditions extends LootModifier {

        public CommonLootAdditions(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            BlockPos pos = context.get(LootParameters.POSITION);
            TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
            ResourceLocation lootTable = null;
            if(te instanceof LockableLootTileEntity){
                LockableLootTileEntity lockableLootTileEntity = (LockableLootTileEntity)te;
                        lootTable =
                        ObfuscationReflectionHelper.getPrivateValue(
                                LockableLootTileEntity.class,
                                lockableLootTileEntity,
                                "field_184284_m");
                if (lootTable != null) {
                    String lootTablePath = lootTable.toString();
                    DungeonsGearConfig.COMMON_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.COMMON_LOOT_TABLES_BLACKLIST.get().contains(lootTablePath)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.UNIQUE_ITEM_COMMON_LOOT.get(),
                                    DungeonsGearConfig.ARTIFACT_COMMON_LOOT.get()));
                        }
                    });
                }

            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<CommonLootAdditions> {

            @Override
            public CommonLootAdditions read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new CommonLootAdditions(conditionsIn);
            }
        }
    }

    public static class UncommonLootAdditions extends LootModifier {

        public UncommonLootAdditions(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            BlockPos pos = context.get(LootParameters.POSITION);
            TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
            ResourceLocation lootTable = null;
            if(te instanceof LockableLootTileEntity){
                LockableLootTileEntity lockableLootTileEntity = (LockableLootTileEntity)te;
                lootTable =
                        ObfuscationReflectionHelper.getPrivateValue(
                                LockableLootTileEntity.class,
                                lockableLootTileEntity,
                                "field_184284_m");
                if (lootTable != null) {
                    String lootTablePath = lootTable.toString();
                    DungeonsGearConfig.UNCOMMON_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.UNCOMMON_LOOT_TABLES_BLACKLIST.get().contains(lootTablePath)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.UNIQUE_ITEM_UNCOMMON_LOOT.get(),
                                    DungeonsGearConfig.ARTIFACT_UNCOMMON_LOOT.get()));
                        }
                    });
                }

            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<UncommonLootAdditions> {

            @Override
            public UncommonLootAdditions read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new UncommonLootAdditions(conditionsIn);
            }
        }
    }

    public static class RareLootAdditions extends LootModifier {

        public RareLootAdditions(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            BlockPos pos = context.get(LootParameters.POSITION);
            TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
            ResourceLocation lootTable = null;
            if(te instanceof LockableLootTileEntity){
                LockableLootTileEntity lockableLootTileEntity = (LockableLootTileEntity)te;
                lootTable =
                        ObfuscationReflectionHelper.getPrivateValue(
                                LockableLootTileEntity.class,
                                lockableLootTileEntity,
                                "field_184284_m");
                if (lootTable != null) {
                    String lootTablePath = lootTable.toString();
                    DungeonsGearConfig.RARE_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.RARE_LOOT_TABLES_BLACKLIST.get().contains(lootTablePath)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.UNIQUE_ITEM_RARE_LOOT.get(),
                                    DungeonsGearConfig.ARTIFACT_RARE_LOOT.get()));
                        }
                    });
                }

            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<RareLootAdditions> {

            @Override
            public RareLootAdditions read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new RareLootAdditions(conditionsIn);
            }
        }
    }

    public static class SuperRareLootAdditions extends LootModifier {

        public SuperRareLootAdditions(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            BlockPos pos = context.get(LootParameters.POSITION);
            TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
            ResourceLocation lootTable = null;
            if(te instanceof LockableLootTileEntity){
                LockableLootTileEntity lockableLootTileEntity = (LockableLootTileEntity)te;
                lootTable =
                        ObfuscationReflectionHelper.getPrivateValue(
                                LockableLootTileEntity.class,
                                lockableLootTileEntity,
                                "field_184284_m");
                if (lootTable != null) {
                    String lootTablePath = lootTable.toString();

                    DungeonsGearConfig.SUPER_RARE_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.SUPER_RARE_LOOT_TABLES_BLACKLIST.get().contains(lootTablePath)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.UNIQUE_ITEM_SUPER_RARE_LOOT.get(),
                                    DungeonsGearConfig.ARTIFACT_SUPER_RARE_LOOT.get()));
                        }
                    });

                }

            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<SuperRareLootAdditions> {

            @Override
            public SuperRareLootAdditions read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new SuperRareLootAdditions(conditionsIn);
            }
        }
    }

    private static void setLootTableNull(LockableLootTileEntity lockableLootTileEntity) {
        // must set it null by this point
        ObfuscationReflectionHelper.setPrivateValue(LockableLootTileEntity.class,
                lockableLootTileEntity,
                null,
                "field_184284_m");
    }
}
