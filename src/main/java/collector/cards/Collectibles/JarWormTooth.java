package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class JarWormTooth extends AbstractCollectibleCard {
    public final static String ID = makeID("JarWormTooth");

    public JarWormTooth() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseDamage = 1;
        baseBlock = 2;
        baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p,magicNumber));
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}