package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);

        baseBlock = 6;
        baseMagicNumber = magicNumber = 6;
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        //tags.add(ChampMod.COMBO);
        //tags.add(ChampMod.COMBOBERSERKER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();

    }

    @Override
    protected void applyPowersToBlock() {
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                int realBaseBlock = this.baseBlock;
                super.applyPowersToBlock();
                this.magicNumber = this.baseMagicNumber = this.block;
                if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
                    baseBlock += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
                }
                super.applyPowersToBlock();
                this.baseBlock = realBaseBlock;// 75
                this.isBlockModified = block != baseBlock;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];// 73
                initializeDescription();
            }
        } else {
            this.baseBlock = this.block = upgraded ? 9 : 6;
            this.isBlockModified = false;
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;// 73
        this.initializeDescription();// 81
    }// 82

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(3);
    }
}