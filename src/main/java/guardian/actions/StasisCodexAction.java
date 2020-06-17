package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;

public class StasisCodexAction extends AbstractGameAction {
    public static int numPlaced;
    private boolean retrieveCard = false;

    public StasisCodexAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.codexOpen();
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                    AbstractCard codexCard = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
                    codexCard.current_x = -1000.0F * Settings.scale;

                    if (GuardianMod.canSpawnStasisOrb()) {
                        AbstractDungeon.actionManager.addToTop(new ChannelAction(new StasisOrb(codexCard, false)));
                    } else {
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, GuardianCharacter.TEXT[6], true));
                    }

                    AbstractDungeon.cardRewardScreen.codexCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}
