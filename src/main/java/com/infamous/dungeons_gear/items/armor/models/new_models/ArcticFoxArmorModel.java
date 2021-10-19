package com.infamous.dungeons_gear.items.armor.models.new_models;

import com.infamous.dungeons_gear.items.armor.models.PatriganDungeonsArmorBaseModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ArcticFoxArmorModel<T extends LivingEntity> extends PatriganDungeonsArmorBaseModel<T> {


    private final ModelRenderer LeftArmArmor_r1;
    private final ModelRenderer RightArmArmor_r1;


    public ArcticFoxArmorModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
        super(modelSize, slot, entity);
        Head.texOffs(0, 0).addBox(-5.0F, -10.0F - getTestificateShiftUp(), -5.0F, 10.0F, 8.0F, 10.0F, 0.25F, false);
        Head.texOffs(4, 2).addBox(2.25F, -13.0F - getTestificateShiftUp(), 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);
        Head.texOffs(4, 2).addBox(-5.25F, -13.0F - getTestificateShiftUp(), 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);
        Head.texOffs(0, 0).addBox(2.25F, -13.0F - getTestificateShiftUp(), -1.0F, 3.0F, 0.0F, 2.0F, 0.0F, false);
        Head.texOffs(0, 0).addBox(-5.25F, -13.0F - getTestificateShiftUp(), -1.0F, 3.0F, 0.0F, 2.0F, 0.0F, false);

        Body.texOffs(6, 25).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 12.0F, 5.0F, 0.26F, false);

        RightArmArmor_r1 = new ModelRenderer(this);
        RightArmArmor_r1.setPos(0F, 0F, 0.0F);
        RightArm.addChild(RightArmArmor_r1);
        setRotationAngle(RightArmArmor_r1, 0.0F, 1.5708F, 0.0F);
        RightArmArmor_r1.texOffs(45, 14).addBox(-2.5F, -2.25F, -4.25F, 5.0F, 12.0F, 4.0F, 0.25F, false);

        LeftArmArmor_r1 = new ModelRenderer(this);
        LeftArmArmor_r1.setPos(0F, 0F, 0.0F);
        LeftArm.addChild(LeftArmArmor_r1);
        setRotationAngle(LeftArmArmor_r1, 0.0F, -1.5708F, 0.0F);
        LeftArmArmor_r1.texOffs(45, 14).addBox(-2.5F, -2.25F, -4.25F, 5.0F, 12.0F, 4.0F, 0.25F, true);

        RightLeg.texOffs(7, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        LeftLeg.texOffs(7, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
    }
}
