package timeeater.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.powers.HastePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class StormOfGold extends AbstractTimeEaterCard {
    public final static String ID = makeID("StormOfGold");
    // intellij stuff skill, self, rare, , , , , 1, 1

    public StormOfGold() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new Shiv();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HastePower(magicNumber));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = BaseMod.MAX_HAND_SIZE - p.hand.size();
                AbstractCard q = new Shiv();
                if (upgraded) q.upgrade();
                att(new MakeTempCardInHandAction(q, x));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
        AbstractCard q = new Shiv();
        q.upgrade();
        cardsToPreview = q;
    }
}