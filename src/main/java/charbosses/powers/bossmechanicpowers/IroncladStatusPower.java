//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Purity;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import hermit.util.Wiz;

public class IroncladStatusPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:IroncladStatusPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public IroncladStatusPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (downfallMod.useLegacyBosses) {
            this.amount = 0;
        } else {
            this.amount = 40;
        }
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }


    @Override
    public int onLoseHp(int damageAmount) {

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {

            this.amount -= damageAmount;
            if (amount <= 0){
                amount = 40;
                AbstractCard c = new Purity();
                CardModifierManager.addModifier(c, new RetainMod());
                Wiz.makeInHand(c);
            }
        }

        return super.onLoseHp(damageAmount);
    }

    public void updateDescription() {

        if (downfallMod.useLegacyBosses) {
            this.description = DESC[0];
        } else {
            if (AbstractDungeon.ascensionLevel >= 19){

                this.description = DESC[3] + amount + DESC[2];
            } else {
                this.description = DESC[1] + amount + DESC[2];
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
