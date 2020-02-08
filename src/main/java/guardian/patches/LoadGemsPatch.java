package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

@SpirePatch(
        clz = CardCrawlGame.class,
        method = "loadPlayerSave"
)
public class LoadGemsPatch {
    public LoadGemsPatch() {
    }

    public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
        GuardianMod.logger.info("Loading saved gems");
        for (AbstractCard c : p.masterDeck.group) {
            if (c instanceof AbstractGuardianCard) {
                GuardianMod.logger.info("Found guardian card to load");
                ((AbstractGuardianCard) c).loadGemMisc();
            }
        }
    }
}
