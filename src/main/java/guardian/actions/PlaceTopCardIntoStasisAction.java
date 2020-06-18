package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;


public class PlaceTopCardIntoStasisAction extends AbstractGameAction {
    private int numCards;
    private boolean talked = false;

    public PlaceTopCardIntoStasisAction(int numCards) {
        this.actionType = ActionType.DAMAGE;
        this.numCards = numCards;
    }

    public void update() {
        if (this.numCards == 0) {
            this.isDone = true;
        }

        if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            this.isDone = true;
        } else {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                AbstractDungeon.actionManager.addToBottom(new PlaceTopCardIntoStasisAction(this.numCards));

            } else {
                if (GuardianMod.canSpawnStasisOrb()) {
                    if (!AbstractDungeon.player.hasEmptyOrb()) {
                        GuardianMod.logger.info("passed has empty orb");
                        for (AbstractOrb o : AbstractDungeon.player.orbs) {
                            if (!(o instanceof StasisOrb)) {
                                GuardianMod.logger.info("found non-stasis orb");
                                AbstractDungeon.player.orbs.remove(o);
                                AbstractDungeon.player.orbs.add(0, o);
                                AbstractDungeon.player.evokeOrb();
                                break;
                            }
                        }
                    }
                    AbstractDungeon.actionManager.addToTop(new ChannelAction(new StasisOrb(AbstractDungeon.player.drawPile.getTopCard(), false)));
                    //AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                } else {
                    if (!talked) {
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, GuardianCharacter.TEXT[6], true));
                        talked = true;
                    }
                }
            }
        }

        this.isDone = true;
    }
}
