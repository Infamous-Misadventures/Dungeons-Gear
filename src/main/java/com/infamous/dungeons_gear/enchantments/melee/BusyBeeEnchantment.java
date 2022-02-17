package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.capabilities.minionmaster.summon.SummonHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class BusyBeeEnchantment extends DungeonsEnchantment {

    public BusyBeeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBusyBeeKill(LivingDeathEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if(event.getSource().getEntity() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();
            if(attacker != null){
                ItemStack mainhand = attacker.getMainHandItem();
                if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.BUSY_BEE)){
                    int busyBeeLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.BUSY_BEE, mainhand);
                    float busyBeeRand = attacker.getRandom().nextFloat();
                    float busyBeeChance = (float) (DungeonsGearConfig.BUSY_BEE_BASE_CHANCE.get() + busyBeeLevel * DungeonsGearConfig.BUSY_BEE_CHANCE_PER_LEVEL.get());
                    if(busyBeeRand <= busyBeeChance) {
                        SummonHelper.summonBee(attacker, victim.blockPosition());
                    }
                }
            }
        }
    }
}
