package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.powers.BurnPower;

public class HeatShield extends AbstractHexaCard {

    public final static String ID = makeID("HeatShield");

    //stupid intellij stuff SKILL, SELF_AND_ENEMY, UNCOMMON

    public HeatShield() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 5;
        this.isEthereal = true;
    }

    @Override
    protected void applyPowersToBlock() {
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                int realBaseBlock = this.baseBlock;
                super.applyPowersToBlock();
                this.magicNumber = this.baseMagicNumber = this.block;
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (mo.hasPower(BurnPower.POWER_ID))
                        baseBlock += mo.getPower(BurnPower.POWER_ID).amount;
                }
                super.applyPowersToBlock();
                this.baseBlock = realBaseBlock;// 75
                this.isBlockModified = block != baseBlock;
                if (upgraded){
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0];// 73
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];// 73
                }
                initializeDescription();
            }
        } else {
            this.baseBlock = this.block = 5;
            this.magicNumber = 5;
            this.isBlockModified = false;
        }
    }

    public void onMoveToDiscard() {
        if (upgraded){
            this.rawDescription = UPGRADE_DESCRIPTION;// 73
        } else {
            this.rawDescription = DESCRIPTION;// 73
        }
        this.initializeDescription();// 81
    }// 82

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}