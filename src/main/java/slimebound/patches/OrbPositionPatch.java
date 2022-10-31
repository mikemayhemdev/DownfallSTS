package slimebound.patches;

import charbosses.orbs.AbstractEnemyOrb;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = AbstractOrb.class, method = "setSlot",
        paramtypez = {
                int.class,
                int.class})
public class OrbPositionPatch {


    public static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {

        if (AbstractDungeon.player instanceof SlimeboundCharacter && !(abstractOrb_instance instanceof AbstractEnemyOrb)) {
            float xStartOffset = AbstractDungeon.player.drawX + Settings.scale * -150F;
            float yStartOffset = AbstractDungeon.player.drawY + Settings.scale * -130F;
            float ySpaceAlternatingOffset = -20 * Settings.scale;
            float xSpaceBetweenSlots = 110 * Settings.scale;

            abstractOrb_instance.tX = xStartOffset + (xSpaceBetweenSlots * slotNum);
            abstractOrb_instance.tY = yStartOffset +  (slotNum % 2) * ySpaceAlternatingOffset;

            abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);

            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();

        }

    }

}

