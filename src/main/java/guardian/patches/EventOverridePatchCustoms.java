package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import guardian.characters.GuardianCharacter;
import guardian.events.*;


@SpirePatch(clz=AbstractDungeon.class,method="initializeCardPools")
public class EventOverridePatchCustoms {


    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
        } else {
            dungeon_instance.eventList.remove(AccursedBlacksmithGuardian.ID);
            dungeon_instance.shrineList.remove(TransmogrifierGuardian.ID);
            dungeon_instance.shrineList.remove(UpgradeShrineGuardian.ID);
            dungeon_instance.shrineList.remove(PurificationShrineGuardian.ID);
            dungeon_instance.eventList.remove(BackToBasicsGuardian.ID);
            dungeon_instance.eventList.remove(GemMine.ID);
            dungeon_instance.eventList.remove(StasisEgg.ID);
            dungeon_instance.eventList.remove(CrystalForge.ID);

        }


    }
}