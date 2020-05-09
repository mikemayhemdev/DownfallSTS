package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;
import slimebound.SlimeboundMod;
import slimebound.relics.ScrapOozeRelic;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.*;

@SpirePatch(clz = CampfireTokeEffect.class, method = "update")
public class TokePatch {


    public static void Prefix() {

        if (AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID) && SlimeboundMod.scrapping && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forPurge) {
            SlimeboundMod.scrapping = false;
            int amount = 0;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            ScrapOozeRelic scrapOoze = ((ScrapOozeRelic) AbstractDungeon.player.getRelic(ScrapOozeRelic.ID));

            if (card.rarity == RARE) {
                amount = 2;
            }
            if (card.rarity == UNCOMMON) {
                amount = 1;
            }
            if (card.rarity == COMMON || card.rarity == BASIC) {
                amount = -1;
            }
            if (card.rarity == CURSE) {
                amount = -2;
            }
            scrapOoze.incrementScrapNum(amount);

        }

    }
}

