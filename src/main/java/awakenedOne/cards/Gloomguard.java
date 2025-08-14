package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Gloomguard extends AbstractAwakenedCard {
    public final static String ID = makeID(Gloomguard.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Gloomguard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        //  baseMagicNumber = magicNumber = 3;
        loadJokeCardImage(this, makeBetaCardPath(Gloomguard.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractCard c : p.hand.group) {
            if (c instanceof VoidCard || c instanceof IntoTheVoid) {
                Wiz.atb(new ExhaustSpecificCardAction(c, p.hand));
                Wiz.atb(new GainEnergyAction(1));
            }
        }
    }

    public void upp() {
        upgradeBlock(3);
    }
}