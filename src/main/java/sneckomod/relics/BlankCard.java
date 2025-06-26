package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import expansioncontent.actions.EchoACardAction;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;

public class BlankCard extends CustomRelic {

    public static final String ID = SneckoMod.makeID("BlankCard");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("BlankCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("BlankCard.png"));
    public boolean activated = false;

    public BlankCard() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STATUS.NAMES[0]), GameDictionary.keywords.get(GameDictionary.STATUS.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.CURSE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.CURSE.NAMES[0])));
    }

    @Override
    public void atBattleStart() {
        ArrayList<AbstractCard> possCardsList = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        possCardsList.removeIf(c -> (c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.type == STATUS));
        AbstractCard card2 = possCardsList.get(AbstractDungeon.cardRandomRng.random(possCardsList.size() - 1)).makeStatEquivalentCopy();
        if (card2 instanceof AbstractUnknownCard) {
            card2 = ((AbstractUnknownCard) card2).generateFromPoolButNotIntoHand();
        } // Get one of the cards in the unknown pool instead of the Unknown card which is already free to play.
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        card2.freeToPlayOnce = true;
        addToBot(new EchoACardAction(card2));
    }

//    @Override
//    public void atBattleStart() {
//        activated = false;
//        beginLongPulse();
//    }

//    @Override
//    public void onCardDraw(AbstractCard card) {
//        if (!activated)
//            addToBot(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    isDone = true;
//                    if (card.cost == 3 && !activated) {
//                        stopPulse();
//                        flash();
//                        activated = true;
//                        addToBot(new MuddleAction(card));
//                    }
//                }
//            });
//    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
