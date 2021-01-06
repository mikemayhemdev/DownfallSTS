package collector.powers;

import basemod.interfaces.CloneablePowerInterface;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

public class Omen extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = CollectorMod.makeID("Omen");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Omen(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("wraithForm");

        this.updateDescription();
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            if (!action.target.isDying && action.target.currentHealth > 0 && !action.target.isEscaping) {
                if (action.target.hasPower(CollectorMod.Afflictions.get(0)) && action.target.hasPower(CollectorMod.Afflictions.get(1)) &&
                        action.target.hasPower(CollectorMod.Afflictions.get(2)) && action.target.hasPower(CollectorMod.Afflictions.get(3)) &&
                        action.target.hasPower(CollectorMod.Afflictions.get(4)) && action.target.hasPower(CollectorMod.Afflictions.get(5))
                ) {
                    addToBot(new VFXAction(new CollectorCurseEffect(action.target.drawX, action.target.drawY)));
                    addToBot(new LoseHPAction(action.target, action.target, amount));
                }
            }
        }
    }
    @Override
    public AbstractPower makeCopy() {
        return new Omen(amount);
    }

}
