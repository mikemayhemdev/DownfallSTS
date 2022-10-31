package hermit.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;
import java.util.UUID;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hermit.patches.EnumPatch;

import java.util.Iterator;

public class MagnumAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private AbstractCard magnumcard;
    private boolean isRandom;
    private boolean endTurn;
    public static int numDiscarded;
    private static final float DURATION;
    private DamageInfo info;


    public MagnumAction(AbstractCreature target, DamageInfo info, AbstractCard magnumcard) {
        this.p = AbstractDungeon.player;
        this.magnumcard = magnumcard;
        this.setValues(target, p, 6);
        this.actionType = ActionType.DISCARD;
        this.endTurn = false;
        this.info = info;
        this.duration = DURATION;
        isRandom=false;
    }

    public void update() {
        AbstractCard c;
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            int i;
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                i = this.p.hand.size();

                for(int n = 0; n < i; ++n) {
                    c = this.p.hand.getTopCard();
                    this.p.hand.moveToDiscardPile(c);
                    int cost = c.cost;
                    this.addToTop(new MagnumActionDamage(this.target, this.p,magnumcard));
                    if (!this.endTurn) {
                        c.triggerOnManualDiscard();
                    }

                    GameActionManager.incrementDiscard(this.endTurn);
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (!this.isRandom) {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    this.tickDuration();
                    return;
                }

                numDiscarded = this.amount;
                if (this.p.hand.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            for(i = 0; i < this.amount; ++i) {
                c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.moveToDiscardPile(c);
                int cost = c.cost;
                this.addToTop(new MagnumActionDamage(this.target, this.p,magnumcard));
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(this.endTurn);
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while (var4.hasNext()) {
                    c = (AbstractCard) var4.next();
                    this.p.hand.moveToDiscardPile(c);
                    int cost = c.cost;
                    this.addToTop(new MagnumActionDamage(this.target, this.p, magnumcard));
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(this.endTurn);
                }
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}