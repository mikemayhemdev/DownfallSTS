package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;


import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class ImpendingDoom extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(ImpendingDoom.class.getSimpleName());
    public static final String IMG = makeCardPath("impending_doom.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = -2;

    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 1;

    // /STAT DECLARATION/

    public ImpendingDoom() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        loadJokeCardImage(this, "impending_doom.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 13, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            this.addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(13, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }

    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = false;

        if (isDeadOnPos()) {
            //System.out.println("Impending doom trigger, cards in hand: " + AbstractDungeon.player.hand.size() + " Position: " + AbstractDungeon.player.hand.group.indexOf(this));
            onDeadOn();
            this.dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        }

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
    }
}