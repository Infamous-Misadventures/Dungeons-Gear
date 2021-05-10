package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ProspectorEnchantment extends Enchantment{

    public ProspectorEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onProspectiveKill(LivingDropsEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            ItemStack mainhand = attacker.getHeldItemMainhand();
            LivingEntity victim = event.getEntityLiving();
            boolean uniqueWeaponFlag = mainhand.getItem() == ItemRegistry.THE_LAST_LAUGH.get() || mainhand.getItem() == ItemRegistry.DIAMOND_PICKAXE.get();
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.PROSPECTOR)){
                int prospectorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.PROSPECTOR, mainhand);
                float prospectorChance;
                prospectorChance = 0.25F * prospectorLevel;
                float prospectorRand = attacker.getRNG().nextFloat();
                if(prospectorRand <= prospectorChance){
                    if(victim instanceof MonsterEntity && !isInNether(victim)){
                        ItemEntity drop = new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), new ItemStack(Items.EMERALD, 1));
                        event.getDrops().add(drop);
                    }
                    else if(victim instanceof MonsterEntity && isInNether(victim)){
                        ItemEntity drop = new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), new ItemStack(Items.GOLD_INGOT, 1));
                        event.getDrops().add(drop);
                    }
                }
            }
            if(uniqueWeaponFlag){
                float prospectorRand = attacker.getRNG().nextFloat();
                if(prospectorRand <= 0.25F) {
                    if (victim instanceof MonsterEntity && !isInNether(victim)) {
                        ItemEntity drop = new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), new ItemStack(Items.EMERALD, 1));
                        event.getDrops().add(drop);
                    } else if (victim instanceof MonsterEntity && isInNether(victim)) {
                        ItemEntity drop = new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), new ItemStack(Items.GOLD_INGOT, 1));
                        event.getDrops().add(drop);
                    }
                }
            }
        }
    }

    private static boolean isInNether(LivingEntity victim){
        return victim.getEntityWorld().getDimensionKey() == World.THE_NETHER;
    }
}
