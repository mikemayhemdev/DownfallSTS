/*
package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.PurificationShrineEvil;
import com.megacrit.cardcrawl.events.shrines.TransmogrifierEvil;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrineEvil;
import guardian.characters.GuardianCharacter;
import guardian.events.PurificationShrineGuardian;
import guardian.events.TransmogrifierGuardian;
import guardian.events.UpgradeShrineGuardian;


@SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
public class EventOverridePatchShrines {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {

            AbstractDungeon.shrineList.remove(PurificationShrineEvil.ID);
            AbstractDungeon.shrineList.remove(TransmogrifierEvil.ID);
            AbstractDungeon.shrineList.remove(UpgradeShrineEvil.ID);

        } else {
            AbstractDungeon.eventList.remove(PurificationShrineGuardian.ID);
            AbstractDungeon.eventList.remove(TransmogrifierGuardian.ID);
            AbstractDungeon.eventList.remove(UpgradeShrineGuardian.ID);
        }


    }
}
*/