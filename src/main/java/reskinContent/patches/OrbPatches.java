package reskinContent.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.patches.GuardianEnum;


import java.lang.reflect.Method;

import static reskinContent.reskinContent.*;

public class OrbPatches
{

    @SpirePatch(clz = AbstractOrb.class,method = "setSlot",paramtypez = {int.class,int.class})
    public static class SetSlotPatch{
        @SpirePrefixPatch
        public  static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance,int slotNum,int maxOrbs){
            if(AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN && !guardianOriginalAnimation){
                     float dist = 230.0F * Settings.scale + maxOrbs * 10.0F * Settings.scale;
                     float angle = 100.0F + maxOrbs * 12.0F;
                     float offsetAngle = angle / 2.0F;
                    angle *= slotNum / (maxOrbs - 1.0F);
                    angle += 90.0F - offsetAngle;
                    abstractOrb_instance.tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
                    abstractOrb_instance.tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;

                    if (maxOrbs == 1) {
                        abstractOrb_instance.tX = AbstractDungeon.player.drawX;
                        abstractOrb_instance.tY = 160.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
                         }

                    abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
                return SpireReturn.Return(null);
            }else{
                return SpireReturn.Continue();
        }
        }
    }
}
