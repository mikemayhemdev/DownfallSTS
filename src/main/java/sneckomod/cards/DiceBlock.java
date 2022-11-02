package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class DiceBlock extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBlock");

    public DiceBlock() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 8;
        tags.add(downfallMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, getRandomNum(magicNumber, block, this)));
    }

    @Override
    protected void applyPowersToBlock() {
        int CURRENT_MAGIC_NUMBER = baseMagicNumber;
        int CURRENT_BLOCK = baseBlock;
        baseBlock = CURRENT_MAGIC_NUMBER;
        super.applyPowersToBlock();
        magicNumber = block;
        isMagicNumberModified = block != baseBlock;

        baseBlock = CURRENT_BLOCK;
        super.applyPowersToBlock();
        isBlockModified = baseBlock != block;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            upgradeBlock(4);
        }
    }
}