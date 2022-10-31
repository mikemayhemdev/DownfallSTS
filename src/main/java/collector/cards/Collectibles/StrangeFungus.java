package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class StrangeFungus extends AbstractCollectibleCard {
    public final static String ID = makeID("StrangeFungus");

    public StrangeFungus() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber =  baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new VulnerablePower(m,1,false));
        applyToSelf(new StrengthPower(m,magicNumber));
        applyToSelf(new LoseStrengthPower(m,magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}