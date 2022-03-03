//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HermitWheelOfFortune extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitWheelOfFortune";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public HermitWheelOfFortune(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        flash();
        if (this.owner instanceof CharBossHermit) {
            if (((CharBossHermit) this.owner).chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                ((ArchetypeAct2WheelOfFateNewAge) ((CharBossHermit) this.owner).chosenArchetype).reInitializeHand();
            }
        }
        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
