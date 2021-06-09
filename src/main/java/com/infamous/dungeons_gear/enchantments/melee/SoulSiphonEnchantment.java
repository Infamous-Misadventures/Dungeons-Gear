package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

@Mod.EventBusSubscriber(modid = MODID)
public class SoulSiphonEnchantment extends DungeonsEnchantment {

    public SoulSiphonEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if(!(user instanceof PlayerEntity)) return;
        if(!(target instanceof LivingEntity)) return;
        float chance = user.getRNG().nextFloat();
        if(chance <=  0.1F){
            CapabilityHelper.getComboCapability(user).setSouls(CapabilityHelper.getComboCapability(user).getSouls()+level*3);
            // soul particles
            PROXY.spawnParticles(target, ParticleTypes.SOUL);
        }
    }

    @SubscribeEvent
    public static void onEternalKnifeAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof PlayerEntity)) return;
        PlayerEntity attacker = (PlayerEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        if(hasSoulSiphonBuiltIn(mainhand)){
            float chance = attacker.getRNG().nextFloat();
            if(chance <=  0.1F) {
                CapabilityHelper.getComboCapability(attacker).setSouls(CapabilityHelper.getComboCapability(attacker).getSouls()+3);
                // soul particles
                PROXY.spawnParticles(victim, ParticleTypes.SOUL);
            }
        }
    }

    private static boolean hasSoulSiphonBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasSoulSiphonBuiltIn(mainhand);
    }
}
