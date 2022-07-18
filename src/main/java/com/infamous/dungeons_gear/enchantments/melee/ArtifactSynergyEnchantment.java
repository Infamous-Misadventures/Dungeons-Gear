package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ArtifactSynergyEnchantment extends DungeonsEnchantment {

    public ArtifactSynergyEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onArtifactSynergyAttack(LivingDamageEvent event){
        if(PlayerAttackHelper.isProbablyNotMeleeDamage(event.getSource())) return;
        if(event.getSource() instanceof OffhandAttackDamageSource) return;
        if(!(event.getSource().getEntity() instanceof LivingEntity)) return;
        LivingEntity attacker = (LivingEntity)event.getSource().getEntity();
        ItemStack mainhand = attacker.getMainHandItem();
        Combo comboCap = ComboHelper.getComboCapability(attacker);
        if(comboCap == null) return;

        if(comboCap.hasArtifactSynergy() && !attacker.level.isClientSide){
            comboCap.setArtifactSynergy(false);
            int artifactSynergyLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeEnchantmentList.ARTIFACT_SYNERGY, mainhand);
            if(artifactSynergyLevel > 0){
                float damageMultiplier = 1.2F + artifactSynergyLevel * 0.2F;
                float currentDamage = event.getAmount();
                event.setAmount(currentDamage * damageMultiplier);
            }

        }
    }
}
