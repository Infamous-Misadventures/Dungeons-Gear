package com.infamous.dungeons_gear.loot;

import com.google.gson.JsonObject;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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
            if(!DungeonsGearConfig.COMMON.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            Vector3d vector3d = context.get(LootParameters.field_237457_g_);
            if(vector3d == null) return generatedLoot;
            BlockPos pos = new BlockPos(vector3d);
            TileEntity te = context.getWorld().getTileEntity(pos);
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
                    DungeonsGearConfig.Common.COMMON_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.Common.COMMON_LOOT_TABLES_BLACKLIST.get().contains(path)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.COMMON.UNIQUE_ITEM_COMMON_LOOT.get(),
                                    DungeonsGearConfig.COMMON.ARTIFACT_COMMON_LOOT.get()));
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

            @Override
            public JsonObject write(CommonLootAdditions instance) {
                return null;
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
            if(!DungeonsGearConfig.COMMON.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            Vector3d vector3d = context.get(LootParameters.field_237457_g_);
            if(vector3d == null) return generatedLoot;
            BlockPos pos = new BlockPos(vector3d);
            TileEntity te = context.getWorld().getTileEntity(pos);
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
                    DungeonsGearConfig.Common.UNCOMMON_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.Common.UNCOMMON_LOOT_TABLES_BLACKLIST.get().contains(path)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.COMMON.UNIQUE_ITEM_UNCOMMON_LOOT.get(),
                                    DungeonsGearConfig.COMMON.ARTIFACT_UNCOMMON_LOOT.get()));
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

            @Override
            public JsonObject write(UncommonLootAdditions instance) {
                return null;
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
            if(!DungeonsGearConfig.COMMON.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            Vector3d vector3d = context.get(LootParameters.field_237457_g_);
            if(vector3d == null) return generatedLoot;
            BlockPos pos = new BlockPos(vector3d);
            TileEntity te = context.getWorld().getTileEntity(pos);
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
                    DungeonsGearConfig.Common.RARE_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.Common.RARE_LOOT_TABLES_BLACKLIST.get().contains(path)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.COMMON.UNIQUE_ITEM_RARE_LOOT.get(),
                                    DungeonsGearConfig.COMMON.ARTIFACT_RARE_LOOT.get()));
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

            @Override
            public JsonObject write(RareLootAdditions instance) {
                return null;
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
            if(!DungeonsGearConfig.COMMON.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            Vector3d vector3d = context.get(LootParameters.field_237457_g_);
            if(vector3d == null) return generatedLoot;
            BlockPos pos = new BlockPos(vector3d);
            TileEntity te = context.getWorld().getTileEntity(pos);
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

                    DungeonsGearConfig.Common.SUPER_RARE_LOOT_TABLES.get().forEach((path) ->{
                        if(lootTablePath.contains(path) && !DungeonsGearConfig.Common.SUPER_RARE_LOOT_TABLES_BLACKLIST.get().contains(path)){
                            generatedLoot.addAll(ChestLootHelper.generateLootFromValues(DungeonsGearConfig.COMMON.UNIQUE_ITEM_SUPER_RARE_LOOT.get(),
                                    DungeonsGearConfig.COMMON.ARTIFACT_SUPER_RARE_LOOT.get()));
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

            @Override
            public JsonObject write(SuperRareLootAdditions instance) {
                return null;
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
