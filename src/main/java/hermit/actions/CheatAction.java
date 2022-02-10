package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryEyesEffect;
import com.megacrit.cardcrawl.vfx.scene.IroncladVictoryFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.SilentVictoryStarEffect;
import hermit.cards.AbstractHermitCard;

import java.util.Iterator;

public class CheatAction extends AbstractGameAction {

    public static final String[] TEXT;
    private float startingDuration;
    private AbstractCard cheatcard;
    private AbstractCard onlyChoice;
    private boolean onlyBoolean = false;
    private boolean isdeadon;

    public CheatAction(int numCards, AbstractHermitCard cheatcard, boolean isdeadon) {
        this.amount = numCards;
        this.cheatcard = cheatcard;
        this.isdeadon = isdeadon;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startingDuration) {

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (AbstractDungeon.player.drawPile.size() == 1) {
                    onlyBoolean = true;
                    onlyChoice = AbstractDungeon.player.drawPile.getTopCard();
                    CheatOutCard();
                    this.isDone = true;
                    return;
                }

                CardGroup tmpGroup = new CardGroup(CardGroupType.UNSPECIFIED);
                if (this.amount != -1) {
                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                        tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                    }
                } else {
                    Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();

                    while(var5.hasNext()) {
                        AbstractCard cd = (AbstractCard)var5.next();
                        tmpGroup.addToBottom(cd);
                    }
                }

                AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, true, TEXT[0]);
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                CheatOutCard();
            }

            this.tickDuration();
        }
    }

    public void CheatOutCard()
    {
        Iterator var1;
        AbstractCard c;

        if (onlyBoolean)
        {
            c = onlyChoice;
        }
        else {
            var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
            c = (AbstractCard) var1.next();
        }

        AbstractDungeon.player.drawPile.group.remove(c);
        AbstractDungeon.getCurrRoom().souls.remove(c);
        AbstractDungeon.player.limbo.group.add(c);

        if (this.isdeadon)
        {
            if (c.hasTag(AbstractHermitCard.Enums.DEADON))
            {
                ((AbstractHermitCard)c).trig_deadon = true;
            }
        }

        c.current_y = -200.0F * Settings.scale;
        c.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        c.target_y = (float)Settings.HEIGHT / 2.0F;
        c.targetAngle = 0.0F;
        c.lighten(false);
        c.drawScale = 0.12F;
        c.targetDrawScale = 0.75F;
        c.applyPowers();
        this.addToTop(new NewQueueCardAction(c, true, false, true));
        this.addToTop(new UnlimboAction(c));
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }

        if (!onlyBoolean)
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT;
    }
}
