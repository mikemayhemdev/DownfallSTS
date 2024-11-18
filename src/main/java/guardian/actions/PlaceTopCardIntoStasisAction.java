package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;


public class PlaceTopCardIntoStasisAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    public PlaceTopCardIntoStasisAction() {
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToTop(new PlaceTopCardIntoStasisAction());
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            //Changed from addToBottom as currently that would result in multiple queued copies of this action (which Planning does) queueing many extra shuffles.
        } else {
            if (GuardianMod.canSpawnStasisOrb()) {
                if (!AbstractDungeon.player.hasEmptyOrb()) {
                    //GuardianMod.logger.info("passed has empty orb");
                    for (AbstractOrb o : AbstractDungeon.player.orbs) {
                        if (!(o instanceof StasisOrb)) {
                            //GuardianMod.logger.info("found non-stasis orb");
                            AbstractDungeon.player.orbs.remove(o);
                            AbstractDungeon.player.orbs.add(0, o);
                            //AbstractDungeon.player.evokeOrb();
                            break;
                        }
                    }
                }
                AbstractDungeon.actionManager.addToTop(new ChannelAction(new StasisOrb(AbstractDungeon.player.drawPile.getTopCard(), AbstractDungeon.player.drawPile)));
            } else {
                if (!AbstractDungeon.player.hasEmptyOrb()) {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[5], true));
                }
            }
        }

        this.isDone = true;
    }
}
