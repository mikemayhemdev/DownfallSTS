package awakenedOne.cardmods;

import awakenedOne.cards.tokens.Feather;
import awakenedOne.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class FlyingModifier extends AbstractCardModifier {

    public static String MOD_ID = "awakened:Flying";

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cardsToPreview = new Feather();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group.equals(AbstractDungeon.player.hand)) {
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    card.superFlash(Color.GREEN.cpy());
                    AbstractDungeon.player.hand.moveToDeck(card, true);
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(new Feather()));
                }
            });
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return MOD_ID;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, FlyingModifier.MOD_ID);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FlyingModifier();
    }
}
