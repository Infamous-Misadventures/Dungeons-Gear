package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.utilties.EnchantUtils;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.utilties.AbilityUtils;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.WHISPERING_SPEAR;

@Mod.EventBusSubscriber(modid = MODID)
public class EchoEnchantment extends AOEDamageEnchantment {

    public EchoEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment);
    }

    @SubscribeEvent
    public static void onEchoAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getTrueSource() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getTrueSource();
        if(event.getEntityLiving() == null) return;
        LivingEntity victim = event.getEntityLiving();
        ItemStack mainhand = attacker.getHeldItemMainhand();
        boolean uniqueWeaponFlag = mainhand.getItem() == WHISPERING_SPEAR;
        if(EnchantUtils.hasEnchantment(mainhand, MeleeEnchantmentList.ECHO)){
            int echoLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.ECHO, mainhand);
            float damageDealt = event.getAmount();
            float echoChance = 0;
            if(echoLevel == 1) echoChance = 0.25F;
            if(echoLevel == 2) echoChance = 0.50F;
            if(echoLevel == 3) echoChance = 0.75F;
            float echoRand = attacker.getRNG().nextFloat();
            if(echoRand <= echoChance){
                AbilityUtils.causeEchoAttack(attacker, victim, damageDealt, 3.0f);
            }
        }
        if(uniqueWeaponFlag){
            float damageDealt = event.getAmount();
            float echoRand = attacker.getRNG().nextFloat();
            if(echoRand <= 0.25F){
                AbilityUtils.causeEchoAttack(attacker, victim, damageDealt, 3.0f);
            }
        }
    }
}
