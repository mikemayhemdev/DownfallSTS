package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ScryChantAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.atb;

@Deprecated
@CardIgnore
public class SeverFate extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SeverFate.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public SeverFate() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new ScryChantAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}