package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.FlarePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.applyToSelf;

public class FlareShot extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(FlareShot.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public FlareShot() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(FlareShot.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new FlarePower(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}