package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.PlumeJab;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class Pluck extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Pluck.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Pluck() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        isMultiDamage = true;
        cardsToPreview = new PlumeJab();
        loadJokeCardImage(this, makeBetaCardPath(Pluck.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        Wiz.makeInHand(new PlumeJab(), 1);
        //applyToSelf(new ConjureNextPower(1));
        //atb(new ConjureAction(false));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}