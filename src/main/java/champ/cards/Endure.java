package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Endure extends AbstractChampCard {

    public final static String ID = makeID("Endure");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 6;

    public Endure() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        super.applyPowersToBlock();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }

    public void upp() {
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}