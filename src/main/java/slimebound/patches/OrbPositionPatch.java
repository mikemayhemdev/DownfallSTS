package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.SpawnedSlime;

@SpirePatch(clz= AbstractOrb.class,method="setSlot",
        paramtypez = {
        int.class,
                int.class})
public class OrbPositionPatch {


    public static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {

        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            abstractOrb_instance.tX = ((SlimeboundCharacter) AbstractDungeon.player).orbPositionsX[slotNum];
            abstractOrb_instance.tY = ((SlimeboundCharacter) AbstractDungeon.player).orbPositionsY[slotNum];

            abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();

        }

    }

    }

