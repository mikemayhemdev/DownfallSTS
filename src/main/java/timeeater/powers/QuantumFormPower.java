package timeeater.powers;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import timeeater.cardmods.IncreasedBlockMod;
import timeeater.cardmods.IncreasedDamageMod;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;

public class QuantumFormPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(QuantumFormPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public QuantumFormPower(int amount) {
        super(ID,  PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard c : SuspendHelper.suspendGroup.group) {
                    if (c.baseDamage > -1) {
                        CardModifierManager.addModifier(c, new IncreasedDamageMod(amount));
                    }
                    if (c.baseBlock > -1) {
                        CardModifierManager.addModifier(c, new IncreasedBlockMod(amount));
                    }
                    if (c.baseDamage > -1 || c.baseBlock > -1) {
                        c.superFlash();
                    }
                }
            }
        });
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
