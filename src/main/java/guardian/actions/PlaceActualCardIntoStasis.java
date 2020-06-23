package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;


public class PlaceActualCardIntoStasis extends AbstractGameAction {
    private final AbstractCard card;
    private final CardGroup source;

    private final boolean hadRetain;

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    public PlaceActualCardIntoStasis(AbstractCard card) {
        this(card, null);
    }
  
    public PlaceActualCardIntoStasis(AbstractCard card, CardGroup source) {
        this.card = card;
        this.source = source;
        this.actionType = ActionType.DAMAGE;

        hadRetain = card.retain;
        if (source != null && source.type == CardGroup.CardGroupType.HAND)
        {
            card.retain = true;
        }
    }

    public void update() {
        if (GuardianMod.canSpawnStasisOrb()) {
            if (!AbstractDungeon.player.hasEmptyOrb()) {
                //GuardianMod.logger.info("passed has empty orb");
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (!(o instanceof StasisOrb)) {
                        //GuardianMod.logger.info("found non-stasis orb");
                        AbstractDungeon.player.orbs.remove(o);
                        AbstractDungeon.player.orbs.add(0, o);
                        AbstractDungeon.player.evokeOrb();
                        break;
                    }
                }
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new StasisOrb(card, source)));
        } else {
            card.retain = hadRetain;
            if (!AbstractDungeon.player.hasEmptyOrb()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[5], true));
            }
        }

        this.isDone = true;
    }
}
