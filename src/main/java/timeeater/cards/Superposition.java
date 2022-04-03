package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class Superposition extends AbstractTimeEaterCard {
    public final static String ID = makeID("Superposition");
    // intellij stuff skill, self, rare, , , , , , 

    public Superposition() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new QuantumStrike();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MakeTempCardInDiscardAndDeckAction(new QuantumStrike()));
        atb(new SuspendAction(new QuantumStrike()));
        if (upgraded)
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.player.exhaustPile.addToTop(new QuantumStrike());
                }
            });
    }

    public void upp() {
        uDesc();
    }
}