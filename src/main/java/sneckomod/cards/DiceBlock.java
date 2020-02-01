package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.cards.unknowns.AbstractUnknownCard;

public class DiceBlock extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBlock");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;

    public DiceBlock() {
        super(ID, "Beta", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = getRandomNum(1, block);
        atb(new GainBlockAction(p, x));
    }


    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        for (AbstractCard q : AbstractDungeon.player.masterDeck.group) {
            if (q instanceof AbstractUnknownCard)
                baseBlock += 1;
        }
        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;// 75
        this.isBlockModified = block != baseBlock;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}