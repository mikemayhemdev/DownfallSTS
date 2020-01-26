package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.events.shrines.PurificationShrine;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import guardian.characters.GuardianCharacter;
import guardian.events.PurificationShrineGuardian;
import guardian.events.TransmogrifierGuardian;
import guardian.events.UpgradeShrineGuardian;


@SpirePatch(clz= AbstractDungeon.class,method="initializeCardPools")
public class EventOverridePatchShrines {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {

            AbstractDungeon.shrineList.remove(PurificationShrine.ID);
            AbstractDungeon.shrineList.remove(Transmogrifier.ID);
            AbstractDungeon.shrineList.remove(UpgradeShrine.ID);

        } else {
            AbstractDungeon.eventList.remove(PurificationShrineGuardian.ID);
            AbstractDungeon.eventList.remove(TransmogrifierGuardian.ID);
            AbstractDungeon.eventList.remove(UpgradeShrineGuardian.ID);
        }


    }
}