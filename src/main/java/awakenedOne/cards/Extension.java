package awakenedOne.cards;

import awakenedOne.actions.DrawPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.att;

public class Extension extends AbstractAwakenedCard {
    public final static String ID = makeID(Extension.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Extension() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        loadJokeCardImage(this, makeBetaCardPath(Extension.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        att(new DrawPowerAction(1));
    }

    public void upp() {
        upgradeDamage(4);
    }
}