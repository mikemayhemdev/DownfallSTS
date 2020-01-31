package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import guardian.characters.GuardianCharacter;


@SpirePatch(clz = AbstractDungeon.class, method = "initializeSpecialOneTimeEventList")
public class EventOverridePatchSpecial {

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {

            dungeon_instance.specialOneTimeEventList.remove(AccursedBlacksmith.ID);

        }


    }
}