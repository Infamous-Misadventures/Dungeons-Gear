package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.SoulHelper;
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

import net.minecraft.enchantment.Enchantment.Rarity;

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
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if(!(user instanceof PlayerEntity)) return;
        if(!(target instanceof LivingEntity)) return;
        if( user.getLastHurtMobTimestamp()==user.tickCount)return;
        float chance = user.getRandom().nextFloat();
        if(chance <=  0.1F){
            SoulHelper.addSouls(user, level*3);
            // soul particles
            PROXY.spawnParticles(target, ParticleTypes.SOUL);
        }
    }

    @SubscribeEvent
    public static void onEternalKnifeAttack(LivingAttackEvent event){
        if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getEntity() instanceof PlayerEntity)) return;
        PlayerEntity attacker = (PlayerEntity)event.getSource().getEntity();
        if( attacker.getLastHurtMobTimestamp()==attacker.tickCount)return;
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getMainHandItem();
        if(hasSoulSiphonBuiltIn(mainhand)){
            float chance = attacker.getRandom().nextFloat();
            if(chance <=  0.1F) {
                SoulHelper.addSouls(attacker, 3);
                // soul particles
                PROXY.spawnParticles(victim, ParticleTypes.SOUL);
            }
        }
    }

    private static boolean hasSoulSiphonBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasSoulSiphonBuiltIn(mainhand);
    }
}
