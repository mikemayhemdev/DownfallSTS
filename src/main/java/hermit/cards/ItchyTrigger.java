package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

import java.util.ArrayList;
import java.util.Iterator;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;
// "How come this card extends CustomCard and not DynamicCard like all the rest?"
// Skip this question until you start figuring out the AbstractDefaultCard/AbstractDynamicCard and just extend DynamicCard
// for your own ones like all the other cards.

// Well every card, at the end of the day, extends CustomCard.
// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.
public class ItchyTrigger extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards

     */

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(ItchyTrigger.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("itchy_trigger.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main DefaultMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    // Hey want a second damage/magic/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variabls they personally have and create your own ones.

    // /STAT DECLARATION/

    public ItchyTrigger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;

        this.tags.add(AbstractHermitCard.Enums.DEADON);
        loadJokeCardImage(this, "itchy_trigger.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom( // The action managed queues all the actions a card should do.

                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),

                        EnumPatch.HERMIT_GUN2)); // The animation the damage action uses to hit.
        if (isDeadOn()) {
            onDeadOn();
            this.itchyTrigger();
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    public void itchyTrigger() {

            ArrayList<AbstractCard> groupCopy = new ArrayList();
            Iterator var4 = AbstractDungeon.player.hand.group.iterator();


            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    groupCopy.add(c);
                }
            }

            var4 = AbstractDungeon.actionManager.cardQueue.iterator();

            while(var4.hasNext()) {
                CardQueueItem i = (CardQueueItem)var4.next();
                if (i.card != null) {
                    groupCopy.remove(i.card);
                }
            }

            AbstractCard c = null;
            if (!groupCopy.isEmpty()) {

                Iterator var9 = groupCopy.iterator();

                while(var9.hasNext()) {
                    AbstractCard cc = (AbstractCard)var9.next();
                }

                c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }

            if (c != null) {
                c.setCostForTurn(0);
            }


    }
}
