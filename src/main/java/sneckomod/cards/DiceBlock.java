package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.cards.unknowns.AbstractUnknownCard;

public class DiceBlock extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBlock");

    public DiceBlock() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 3;
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, getRandomNum(magicNumber, block, this)));
        for (int i = 0; i < this.costForTurn; i++) {
            atb(new GainBlockAction(p, getRandomNum(magicNumber, block, this)));
        }
        atb(new MuddleAction(this));
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
            upgradeMagicNumber(1);
            upgradeBlock(2);
        }
    }
}