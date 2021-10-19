package com.infamous.dungeons_gear.items.armor.models.new_models;

import com.infamous.dungeons_gear.items.armor.models.PatriganDungeonsArmorBaseModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class HungryHorrorArmorModel<T extends LivingEntity> extends PatriganDungeonsArmorBaseModel<T> {


    private final ModelRenderer BodyArmor_r1;
    private final ModelRenderer LeftHandArmor_r1;
    private final ModelRenderer RightArmArmor_r1;
    private final ModelRenderer RightHandArmor_r1;


    public HungryHorrorArmorModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
        super(modelSize, slot, entity);

        Head.texOffs(1, 0).addBox(-5.0F, -10.0F - getTestificateShiftUp(), -4.5F, 10.0F, 11.0F, 9.0F, -0.25F, false);

        Body.texOffs(5, 23).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 12.0F, 5.0F, 0.26F, false);

        BodyArmor_r1 = new ModelRenderer(this);
        BodyArmor_r1.setPos(0.0F, 11.5F, -2.25F);
        Body.addChild(BodyArmor_r1);
        setRotationAngle(BodyArmor_r1, 0.48F, 0.0F, 0.0F);
        BodyArmor_r1.texOffs(34, 23).addBox(-4.5F, -9.7734F, -0.1357F, 9.0F, 10.0F, 5.0F, 0.01F, false);

        RightArmArmor_r1 = new ModelRenderer(this);
        RightArmArmor_r1.setPos(0F, 0F, 0.0F);
        RightArm.addChild(RightArmArmor_r1);
        setRotationAngle(RightArmArmor_r1, 0.0F, -1.5708F, 0.0F);
        RightArmArmor_r1.texOffs(39, 0).addBox(-3.0F, -2.0F, -0.75F, 6.0F, 4.0F, 5.0F, 0.25F, false);
        RightArmArmor_r1.texOffs(45, 10).addBox(-2.0F, 2.5F, -0.25F, 4.0F, 3.0F, 3.0F, 0.5F, false);

        RightHandArmor_r1 = new ModelRenderer(this);
        RightHandArmor_r1.setPos(0.0F, 0.0F, 0.0F);
        RightArm.addChild(RightHandArmor_r1);
        setRotationAngle(RightHandArmor_r1, 0.0F, 1.5708F, 0.0F);
        RightHandArmor_r1.texOffs(46, 55).addBox(-3.0F, 6.5F, -2.75F, 6.0F, 3.0F, 2.0F, 0.5F, false);
        RightHandArmor_r1.texOffs(27, 54).addBox(-3.0F, 10.5F, -2.75F, 6.0F, 2.0F, 3.0F, 0.5F, false);

        LeftHandArmor_r1 = new ModelRenderer(this);
        LeftHandArmor_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftArm.addChild(LeftHandArmor_r1);
        setRotationAngle(LeftHandArmor_r1, 0.0F, -1.5708F, 0.0F);
        LeftHandArmor_r1.texOffs(46, 44).addBox(-3.0F, 6.5F, -2.75F, 6.0F, 3.0F, 2.0F, 0.5F, false);
        LeftHandArmor_r1.texOffs(27, 44).addBox(-3.0F, 10.5F, -2.75F, 6.0F, 2.0F, 3.0F, 0.5F, false);

        RightLeg.texOffs(7, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        LeftLeg.texOffs(7, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, true);
    }
}
