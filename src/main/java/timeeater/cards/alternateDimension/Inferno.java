package timeeater.cards.alternateDimension;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.actions.TackleSelfDamageAction;

import static timeeater.TimeEaterMod.makeID;

public class Inferno extends AbstractDimensionalCard {
    public final static String ID = makeID("Inferno");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Inferno() {
        super(ID, 1, CardType.ATTACK, CardTarget.ALL);
        baseDamage = 20;
        exhaust = true;
        setFrame("infernoframe.png");
        isMultiDamage = true;

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        addToBot(new TackleSelfDamageAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));


    }

    public void upp() {
        upgradeBaseCost(0);
    }
}