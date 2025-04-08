package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

import static sneckomod.util.ColorfulCardReward.TEXT;

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

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            if (!isDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }
        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, TEXT[2]);
    }

    private boolean isDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        return cardsList.stream().anyMatch(c -> c.cardID.equals(card.cardID));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
