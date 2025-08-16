//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;

public class IroncladMushroomPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:IroncladMushroomPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public IroncladMushroomPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        if (!downfallMod.useLegacyBosses){
            this.description = DESC[1] + amount + DESC[2];
        } else {
            this.description = DESC[0];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
