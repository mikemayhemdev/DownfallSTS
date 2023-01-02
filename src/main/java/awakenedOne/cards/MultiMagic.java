package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.awakenedAmt;

public class MultiMagic extends AbstractAwakenedCard {
    public final static String ID = makeID(MultiMagic.class.getSimpleName());
    // intellij stuff attack, enemy, common, 10, 3, 8, 2, 1, 1

    public MultiMagic() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseBlock = 8;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmt();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (amt >= 1) {
            blck();
        }
        if (amt >= 3) {
            atb(new DrawCardAction(magicNumber));
        }
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}