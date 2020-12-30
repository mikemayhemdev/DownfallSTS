package collector.cards.Collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class IroncladMask extends AbstractCollectibleCard {
    public final static String ID = makeID("IroncladMask");

    public IroncladMask() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 10;
        baseBlock = 1;
        baseMagicNumber = 0;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(StrengthPower.POWER_ID)){
            applyToSelf(new StrengthPower(p,p.getPower(StrengthPower.POWER_ID).amount));
        }
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

    }

    @Override
    public void upp() {
        upgradeDamage(10);
    }
}