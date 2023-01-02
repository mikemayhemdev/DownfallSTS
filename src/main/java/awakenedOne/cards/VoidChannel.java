package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class VoidChannel extends AbstractAwakenedCard {
    public final static String ID = makeID(VoidChannel.class.getSimpleName());
    // intellij stuff skill, self, basic, , , , , 1, 1

    public VoidChannel() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new EnergizedPower(p, magicNumber));
    }

    public void upp() {
        upgradeDamage(4);
    }
}