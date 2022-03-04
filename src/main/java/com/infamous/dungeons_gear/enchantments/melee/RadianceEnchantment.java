package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.AOECloudHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.CHAINS_CHANCE;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.RADIANCE_CHANCE;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid = MODID)
public class RadianceEnchantment extends DungeonsEnchantment {

    public RadianceEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

//    @SubscribeEvent
//    public static void onRadianceAttack(LivingAttackEvent event) {
//        if (event.getSource().getDirectEntity() != event.getSource().getEntity()) return;
//        if (event.getSource() instanceof OffhandAttackDamageSource) return;
//        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
//        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
//        if (attacker.getLastHurtMobTimestamp() == attacker.tickCount) return;
//        LivingEntity victim = event.getEntityLiving();
//        ItemStack mainhand = attacker.getMainHandItem();
//        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.RADIANCE)) {
//            float chance = attacker.getRandom().nextFloat();
//            int level = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.RADIANCE, mainhand);
//            if (chance <= RADIANCE_CHANCE.get() && !PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) {
//                AOECloudHelper.spawnRegenCloud(attacker, level - 1);
//            }
//        }
//    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(target instanceof LivingEntity)) return;
        if( user.getLastHurtMobTimestamp()==user.tickCount)return;
        float chance = user.getRandom().nextFloat();
        if(chance <=  RADIANCE_CHANCE.get()){
            AOECloudHelper.spawnRegenCloud(user, level - 1);
        }
    }
}
