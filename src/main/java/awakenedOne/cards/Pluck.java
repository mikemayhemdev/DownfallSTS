package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.NextPowerBlockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.applyToSelf;

public class Pluck extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Pluck.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Pluck() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        this.baseMagicNumber = this.magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Pluck.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new NextPowerBlockPower(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}