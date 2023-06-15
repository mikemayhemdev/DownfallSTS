package collector.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.att;

public class Hurting extends AbstractCollectorCard {
    public final static String ID = makeID(Hurting.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 2, , , 14, 2

    public Hurting() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 14;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onRetained() {
        superFlash(Color.PURPLE.cpy());
        baseDamage += magicNumber;
        updateCost(1);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}