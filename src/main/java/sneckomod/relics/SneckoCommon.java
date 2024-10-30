package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class SneckoCommon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoCommon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SealOfApproval.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SealOfApproval.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 5) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c ->
                    c.type == AbstractCard.CardType.POWER && c.rarity == AbstractCard.CardRarity.UNCOMMON);

            if (!isDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }
        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, "Special Bonus Card!");
    }

    private boolean isDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        return cardsList.stream().anyMatch(c -> c.cardID.equals(card.cardID));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
