package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;


public class PlaceRandomCardIntoStasisAction extends AbstractGameAction {
    private int numCards;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    public PlaceRandomCardIntoStasisAction(int numCards) {
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
                AbstractDungeon.actionManager.addToBottom(new PlaceRandomCardIntoStasisAction(this.numCards));

            } else {
                if (GuardianMod.canSpawnStasisOrb()) {
                    AbstractDungeon.actionManager.addToBottom(new ChannelAction(new StasisOrb(AbstractDungeon.player.drawPile.getRandomCard(true), false)));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                    if (this.numCards - 1 > 0)
                        AbstractDungeon.actionManager.addToBottom(new PlaceRandomCardIntoStasisAction(this.numCards - 1));
                } else {
                    if (!AbstractDungeon.player.hasEmptyOrb()) {
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[5], true));
                        this.isDone = true;
                    }
                }
            }
        }

        this.isDone = true;
    }
}
