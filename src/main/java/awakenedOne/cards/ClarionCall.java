package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.VoidRefundPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.applyToSelf;

public class ClarionCall extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(ClarionCall.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ClarionCall() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        loadJokeCardImage(this, makeBetaCardPath(ClarionCall.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new VoidRefundPower(p, 1));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}