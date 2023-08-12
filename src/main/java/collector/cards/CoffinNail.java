package collector.cards;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class CoffinNail extends AbstractCollectorCard {
    public final static String ID = makeID(CoffinNail.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 5, 1, , , 5, 1

    public CoffinNail() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL); // Could have better VFX
    }

    @Override
    public void triggerOnExhaust() {
        atb(new ModifyDamageAction(this.uuid, this.magicNumber));
        applyToSelf(new AddCopyNextTurnPower(this));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}