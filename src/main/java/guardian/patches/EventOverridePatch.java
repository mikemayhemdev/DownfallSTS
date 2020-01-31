package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import guardian.characters.GuardianCharacter;


@SpirePatch(clz = TheCity.class, method = "initializeEventList")
public class EventOverridePatch {

    @SpirePostfixPatch
    public static void Postfix(TheCity dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            dungeon_instance.eventList.remove(BackToBasics.ID);
        }


    }
}