package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.actions.CommandAction;
import slimebound.powers.BuffSecondarySlimeEffectsPower;

public class CommandMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString("slimeboundmod:CommandMod").TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractDungeon.actionManager.addToBottom(new CommandAction());
        checkMinionMaster();
    }

    public static void checkMinionMaster() {
        if (AbstractDungeon.player.hasPower(BuffSecondarySlimeEffectsPower.POWER_ID)) {
            for (int i = 0; i < AbstractDungeon.player.getPower(BuffSecondarySlimeEffectsPower.POWER_ID).amount; i++) {
                AbstractDungeon.actionManager.addToBottom(new CommandAction());
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CommandMod();
    }
}
