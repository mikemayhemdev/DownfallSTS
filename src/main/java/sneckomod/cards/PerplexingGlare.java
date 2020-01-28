package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

public class PerplexingGlare extends AbstractSneckoCard {

    public final static String ID = makeID("PerplexingGlare");

    //stupid intellij stuff SKILL, UNCOMMON, ALL_ENEMY

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public PerplexingGlare() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : monsterList()) {
            int x = getRandomNum(-3, magicNumber);
            applyToEnemy(q, new StrengthPower(q, x));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}