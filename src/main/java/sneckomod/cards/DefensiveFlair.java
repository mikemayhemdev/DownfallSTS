package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveFlair extends AbstractSneckoCard {

    public final static String ID = makeID("DefensiveFlair");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public DefensiveFlair() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q.color != AbstractDungeon.player.getCardColor())
                baseBlock += magicNumber;
        }
        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;// 75
        this.isBlockModified = block != baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}