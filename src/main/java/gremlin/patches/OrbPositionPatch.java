package gremlin.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import gremlin.characters.GremlinCharacter;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.lastCombatMetricKey;

@SpirePatch(clz= AbstractOrb.class,method="setSlot",
        paramtypez = {
                int.class,
                int.class})
public class OrbPositionPatch {
    public static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {

        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if(slotNum < 4) {
                if (((AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) && (lastCombatMetricKey.equals(MonsterHelper.SHIELD_SPEAR_ENC))) {
                    abstractOrb_instance.tX = ((GremlinCharacter) AbstractDungeon.player).orbPositionsXCramp[slotNum] + AbstractDungeon.player.drawX;
                } else {
                    abstractOrb_instance.tX = ((GremlinCharacter) AbstractDungeon.player).orbPositionsX[slotNum] + AbstractDungeon.player.drawX;
                }
                abstractOrb_instance.tY = ((GremlinCharacter) AbstractDungeon.player).orbPositionsY[slotNum];

                abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            }
            else {
                slotNum -= 4;
                maxOrbs -= 4;
                final float dist = 160.0f * Settings.scale + maxOrbs * 10.0f * Settings.scale;
                float angle = 100.0f + maxOrbs * 12.0f;
                final float offsetAngle = angle / 2.0f;
                angle *= slotNum / (maxOrbs - 1.0f);
                angle += 90.0f - offsetAngle;
                abstractOrb_instance.tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
                abstractOrb_instance.tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0f;
                if (maxOrbs == 1) {
                    abstractOrb_instance.tX = AbstractDungeon.player.drawX;
                    abstractOrb_instance.tY = 160.0f * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0f;
                }
                abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            }
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();

        }

    }

}
