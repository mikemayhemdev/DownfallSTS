package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import expansioncontent.actions.EchoACardAction;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.ArrayList;

public class BlankCard extends CustomRelic {

    public static final String ID = SneckoMod.makeID("BlankCard");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("BlankCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("BlankCard.png"));

    public BlankCard() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        ArrayList<AbstractCard> possCardsList = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        AbstractCard card2 = possCardsList.get(AbstractDungeon.cardRandomRng.random(possCardsList.size() - 1)).makeStatEquivalentCopy();
        if (card2 instanceof AbstractUnknownCard) {
            card2 = ((AbstractUnknownCard) card2).generateFromPoolButNotIntoHand();
        } // Get one of the cards in the unknown pool instead of the Unknown card which is already free to play.
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        card2.freeToPlayOnce = true;
        addToBot(new EchoACardAction(card2));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
