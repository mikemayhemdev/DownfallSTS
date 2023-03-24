package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.powers.BurnPower;

public class HeatShield extends AbstractHexaCard {

    public final static String ID = makeID("HeatShield");

    //stupid intellij stuff SKILL, SELF_AND_ENEMY, UNCOMMON

    public HeatShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 0;
        HexaMod.loadJokeCardImage(this, "HeatShield.png");
    }

    @Override
    protected void applyPowersToBlock() {
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                int realBaseBlock = this.baseBlock;
                super.applyPowersToBlock();
                this.magicNumber = this.baseMagicNumber = this.block;
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDead && !mo.isDeadOrEscaped() &&  mo.hasPower(BurnPower.POWER_ID))
                        baseBlock += mo.getPower(BurnPower.POWER_ID).amount;
                }
                super.applyPowersToBlock();
                this.baseBlock = realBaseBlock;// 75
                this.isBlockModified = block != baseBlock;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];// 73
                initializeDescription();
            }
        } else {
            this.baseBlock = this.block = 0;
            this.isBlockModified = false;
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;// 73
        this.initializeDescription();// 81
    }// 82

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (AbstractDungeon.getCurrRoom().monsters != null)
            for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m2.isDeadOrEscaped() && m2.hasPower(BurnPower.POWER_ID)) {
                    atb(new RemoveSpecificPowerAction(m2, m2, m2.getPower(BurnPower.POWER_ID)));
                }
            }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}