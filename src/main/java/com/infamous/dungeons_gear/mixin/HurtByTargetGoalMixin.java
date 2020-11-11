package com.infamous.dungeons_gear.mixin;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;

@Mixin(HurtByTargetGoal.class)
public abstract class HurtByTargetGoalMixin extends TargetGoal {

    @Shadow
    private Class<?>[] field_220797_i;

    public HurtByTargetGoalMixin(MobEntity mobIn, boolean checkSight) {
        super(mobIn, checkSight);
    }

    /**
     * @author the_infamous_1
     * @reason Prevents the NPE caused by !mobentity.isOnSameTeam(this.goalOwner.getRevengeTarget()) when getRevengeTarget() returns null
     * Also prevents ClassCastException when checking if current iterated mob, as a TameableEntity, has an owner
     * Backported from 1.16.x
     */
    @Overwrite
    protected void alertOthers(){
        double targetDistance = this.getTargetDistance();
        List<MobEntity> list = this.goalOwner.world.getLoadedEntitiesWithinAABB(
                this.goalOwner.getClass(),
                (new AxisAlignedBB(
                        this.goalOwner.getPosX(),
                        this.goalOwner.getPosY(),
                        this.goalOwner.getPosZ(),
                        this.goalOwner.getPosX() + 1.0D,
                        this.goalOwner.getPosY() + 1.0D,
                        this.goalOwner.getPosZ() + 1.0D))
                        .grow(targetDistance, 10.0D, targetDistance));
        Iterator iterator = list.iterator();

        while(true) {
            MobEntity mobentity;
            while(true) {
                if (!iterator.hasNext()) {
                    return;
                }

                mobentity = (MobEntity)iterator.next();
                if (this.goalOwner != mobentity
                        && mobentity.getAttackTarget() == null
                        && (!(this.goalOwner instanceof TameableEntity) ||
                        (mobentity instanceof TameableEntity && ((TameableEntity)this.goalOwner).getOwner() == ((TameableEntity)mobentity).getOwner()))
                        && (this.goalOwner.getRevengeTarget() != null && !mobentity.isOnSameTeam(this.goalOwner.getRevengeTarget()))) {
                    if (this.field_220797_i == null) {
                        break;
                    }

                    boolean flag = false;

                    for(Class<?> oclass : this.field_220797_i) {
                        if (mobentity.getClass() == oclass) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        break;
                    }
                }
            }

            this.setAttackTarget(mobentity, this.goalOwner.getRevengeTarget());
        }
    }


    protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
        mobIn.setAttackTarget(targetIn);
    }


}
