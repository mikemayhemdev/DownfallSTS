package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LouseCardPower extends AbstractCollectorPower implements OnLoseTempHpPower {
    public static final String NAME = "LouseCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LouseCardPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        this.loadRegion("closeUp");
    }

    private boolean alreadyTriggered = false; // temp hp loss should REALLY count as HP loss.

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0 && !alreadyTriggered) {
            this.flash();

            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            // addToTop(new ApplyPowerAction(owner, owner, new BlurPower(owner, 1), 1));
            addToTop(new GainBlockAction(owner, amount));
        }

        return damageAmount;
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int onLoseTempHp(DamageInfo info, int i) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && i > 0) {
            this.flash();
            alreadyTriggered = true;

            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            // addToTop(new ApplyPowerAction(owner, owner, new BlurPower(owner, 1), 1));
            addToTop(new GainBlockAction(owner, amount));
        }
        return i;
    }
}