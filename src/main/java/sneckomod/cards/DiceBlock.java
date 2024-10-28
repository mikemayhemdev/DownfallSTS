package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class DiceBlock extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("DiceBlock");

    private static final int BASE_BLOCK = 7;
    private static final int UPG_BASE_BLOCK = 3;
    private static final int BASE_MAGIC = 3; // Base overflow block
    private static final int UPG_MAGIC = 1; // Overflow block increase per upgrade

    public DiceBlock() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BASE_BLOCK;
        baseMagicNumber = BASE_MAGIC;
        SneckoMod.loadJokeCardImage(this, "DiceBlock.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isOverflowActive(this)) {
            addToBot(new GainBlockAction(p, p, magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BASE_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}
