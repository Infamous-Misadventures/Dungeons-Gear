package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ProspectorEnchantment extends DropsEnchantment {

    public ProspectorEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onProspectiveKill(LivingDropsEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrow) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            ItemStack mainhand = attacker.getMainHandItem();
            LivingEntity victim = event.getEntity();
            if(ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.PROSPECTOR.get())){
                int prospectorLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.PROSPECTOR.get(), mainhand);
                float prospectorChance;
                prospectorChance = (float) (DungeonsGearConfig.PROSPECTOR_CHANCE_PER_LEVEL.get() * prospectorLevel);
                float prospectorRand = attacker.getRandom().nextFloat();
                if(prospectorRand <= prospectorChance){
                    if(victim instanceof Monster){
                        ItemEntity drop = getProspectorDrop(attacker, victim);
                        event.getDrops().add(drop);
                    }
                }
            }
        }
    }

    private static ItemEntity getProspectorDrop(LivingEntity attacker, LivingEntity victim) {
        ResourceLocation prospectorLootTable = getProspectorLootTable(victim.getCommandSenderWorld());
        ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) victim.level, victim.blockPosition(), prospectorLootTable, attacker.getRandom());
        return new ItemEntity(victim.level, victim.getX(), victim.getY(), victim.getZ(), itemStack);
    }

    private static ResourceLocation getProspectorLootTable(Level world) {
        ResourceLocation resourceLocation = new ResourceLocation(MODID, "enchantments/prospector/" + world.dimension().location().getPath());
        if(LootTableHelper.lootTableExists((ServerLevel) world, resourceLocation)){
            return resourceLocation;
        }else{
            return new ResourceLocation(MODID, "enchantments/prospector/overworld");
        }
    }

}
