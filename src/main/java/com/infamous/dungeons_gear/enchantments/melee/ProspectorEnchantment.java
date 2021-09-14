package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class ProspectorEnchantment extends DropsEnchantment {

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
            boolean uniqueWeaponFlag = hasProspectorBuiltIn(mainhand);
            if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.PROSPECTOR)){
                int prospectorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.PROSPECTOR, mainhand);
                float prospectorChance;
                prospectorChance = 0.25F * prospectorLevel;
                float prospectorRand = attacker.getRNG().nextFloat();
                if(prospectorRand <= prospectorChance){
                    if(victim instanceof MonsterEntity){
                        ItemEntity drop = getProspectorDrop(attacker, victim);
                        event.getDrops().add(drop);
                    }
                }
            }
            if(uniqueWeaponFlag){
                float prospectorRand = attacker.getRNG().nextFloat();
                if(prospectorRand <= 0.25F) {
                    if (victim instanceof MonsterEntity) {
                        ItemEntity drop = getProspectorDrop(attacker, victim);
                        event.getDrops().add(drop);
                    }
                }
            }
        }
    }

    private static ItemEntity getProspectorDrop(LivingEntity attacker, LivingEntity victim) {
        ResourceLocation prospectorLootTable = getProspectorLootTable(victim.getEntityWorld());
        ItemStack itemStack = LootTableHelper.generateItemStack((ServerWorld) victim.world, victim.getPosition(), prospectorLootTable, attacker.getRNG());
        return new ItemEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ(), itemStack);
    }

    private static ResourceLocation getProspectorLootTable(World world) {
        ResourceLocation resourceLocation = new ResourceLocation(MODID, "enchantments/prospector/" + world.getDimensionKey().getLocation().getPath());
        if(LootTableHelper.lootTableExists((ServerWorld) world, resourceLocation)){
            return resourceLocation;
        }else{
            return new ResourceLocation(MODID, "enchantments/prospector/overworld");
        }
    }

    private static boolean hasProspectorBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasProspectorBuiltIn(mainhand);
    }

}
