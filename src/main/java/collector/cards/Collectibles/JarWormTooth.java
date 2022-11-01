package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class JarWormTooth extends AbstractCollectibleCard {
    public final static String ID = makeID("JarWormTooth");

    public JarWormTooth() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CollectorCardSource.FRONT);
        FrontBlock = FrontBaseBlock = douBlock = douBaseBlock = block = baseBlock = 2;
        magicNumber = baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToFront(new StrengthPower(p, magicNumber));
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}