package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class RareBoosterPack extends CustomRelic {

    public static final String ID = SneckoMod.makeID("RareBoosterPack");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("RareBoosterPack.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("RareBoosterPack.png"));

    public RareBoosterPack() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c ->
                    c.rarity == AbstractCard.CardRarity.RARE);

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
