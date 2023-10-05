package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Soulforge extends AbstractCollectorCard {
    public final static String ID = makeID(Soulforge.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 8, 3, , 

    public Soulforge() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new DrawCardFromCollectionAction());
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractCard q : DrawCardAction.drawnCards) {
                        AbstractCard dupe = q.makeStatEquivalentCopy();
                        if (dupe.canUpgrade()) {
                            dupe.upgrade();
                        }
                        att(new MakeTempCardInHandAction(dupe, 1, true));
                    }
                }
            });
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}

