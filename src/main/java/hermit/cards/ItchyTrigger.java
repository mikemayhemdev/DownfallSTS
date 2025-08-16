package hermit.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
import hermit.powers.SnipePower;

import java.util.ArrayList;
import java.util.Iterator;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class ItchyTrigger extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(ItchyTrigger.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("itchy_trigger.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/

    public ItchyTrigger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;

        this.tags.add(AbstractHermitCard.Enums.DEADON);
        loadJokeCardImage(this, "itchy_trigger.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        if (isDeadOn()) {
            TriggerDeadOnEffect(p,m);
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void itchyTrigger() {
            ArrayList<AbstractCard> groupCopy = new ArrayList();
            Iterator var4 = AbstractDungeon.player.hand.group.iterator();


            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                if (c.costForTurn > 0 && !c.freeToPlayOnce) {
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

                c = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }

            if (c != null) {
                c.setCostForTurn(Math.max(c.costForTurn-this.magicNumber,0));
                c.superFlash(Color.GOLD.cpy());
            }
    }

    @Override
    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m)
    {
        this.itchyTrigger();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
