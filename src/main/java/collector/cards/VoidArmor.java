package collector.cards;

import collector.actions.HealTorchheadAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static collector.CollectorMod.makeID;

public class VoidArmor extends AbstractCollectorCard {
    public final static String ID = makeID(VoidArmor.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public VoidArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 0;

    }
    @Override
    protected void applyPowersToBlock() {
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                int realBaseBlock = this.baseBlock;
                super.applyPowersToBlock();
                this.magicNumber = this.baseMagicNumber = this.block;
                block += AbstractDungeon.player.exhaustPile.size();

                super.applyPowersToBlock();
                this.baseBlock = realBaseBlock;
                this.isBlockModified = block != baseBlock;
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
                initializeDescription();
            }
        } else {
            this.baseBlock = this.block = 0;
            this.isBlockModified = false;
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }


    public void upp() {
        upgradeBlock(3);
    }
}