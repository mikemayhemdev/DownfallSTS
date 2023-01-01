package awakenedOne.cards;

import awakenedOne.cardmods.FlyingModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;

public class WingAttack extends AbstractAwakenedCard {
    public final static String ID = makeID(WingAttack.class.getSimpleName());
    // intellij stuff enemy, attack, common, 16, 4, , , 1, 1

    public WingAttack() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseMagicNumber = magicNumber = 1;
        CardModifierManager.addModifier(this, new FlyingModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}