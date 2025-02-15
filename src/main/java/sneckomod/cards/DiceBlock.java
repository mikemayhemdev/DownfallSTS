package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class DiceBlock extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("DiceBlock");

    private static final int BASE_BLOCK = 5;
    private static final int UPG_BASE_BLOCK = 2;

    public DiceBlock() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BASE_BLOCK;
        SneckoMod.loadJokeCardImage(this, "DiceBlock.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isOverflowActive(this)) {
            blck();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BASE_BLOCK);
        }
    }
}
