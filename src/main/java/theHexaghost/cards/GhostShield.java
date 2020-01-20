package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostShield extends AbstractHexaCard {

    public final static String ID = makeID("GhostShield");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public GhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }


    public static int countCards() {
        int count = 0;// 36
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.isEthereal) {
                count++;
            }
        }
        return count;// 52
    }

    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;// 70
        this.baseBlock += this.magicNumber * countCards();// 71
        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;// 75
        this.isBlockModified = this.block != this.baseBlock;// 78
    }

    public void applyPowers() {
        int realBaseBlock = this.baseBlock;// 85
        this.baseBlock += this.magicNumber * countCards();// 86
        super.applyPowers();// 88
        this.baseBlock = realBaseBlock;// 90
        this.isBlockModified = this.block != this.baseBlock;// 93
    }// 94

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