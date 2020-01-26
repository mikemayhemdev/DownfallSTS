/*
package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.events.AccursedBlacksmithGuardian;
import guardian.events.BackToBasicsGuardian;
import guardian.events.GemMine;
import guardian.events.StasisEgg;
import guardian.relics.BottledStasis;


@SpirePatch(clz= CardGroup.class,method="initializeDeck")
public class BottledStasisDeckRemoval {

    @SpirePostfixPatch
    public static void Postfix(CardGroup obj, CardGroup masterDeck) {
        if (AbstractDungeon.player.hasRelic(BottledStasis.ID)){
            obj.removeCard(((BottledStasis)AbstractDungeon.player.getRelic(BottledStasis.ID)).card);
            GuardianMod.logger.info("Removed bottled stasis card");
        }


    }
}
*/