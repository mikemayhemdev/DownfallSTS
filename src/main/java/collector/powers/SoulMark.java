package collector.powers;

import basemod.interfaces.CloneablePowerInterface;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class SoulMark extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = CollectorMod.makeID("SoulMark");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SoulMark(int amount,AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.loadRegion("constricted");

        this.updateDescription();
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulMark(amount,owner);
    }
    public void atStartOfTurn() {
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }
    @Override
    public void onDeath() {
        if (!owner.hasPower(MinionPower.POWER_ID)){
            System.out.println("Collected!");
            addToTop(new VFXAction(new BiteEffect(owner.drawX,owner.drawY, CollectorMod.characterColor)));
            CollectorCollection.GetCollectible((AbstractMonster) owner);
        }
    }
}