package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.BodySlam;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.powers.SnipePower;


import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class CalledShot extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(CalledShot.class.getSimpleName());
    public static final String IMG = makeCardPath("called_shot.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 0;

    // /STAT DECLARATION/

    public CalledShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Enums.DEADON);
        this.baseDamage=0;
        magicNumber=baseMagicNumber=1;
        loadJokeCardImage(this, "called_shot.png");
    }

    public void getMyName()
    {
        if (!this.upgraded)
            this.rawDescription = cardStrings.DESCRIPTION;
        else
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = AbstractDungeon.player.hand.size();
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), EnumPatch.HERMIT_GUN2));

        this.getMyName();
        this.initializeDescription();

        if (isDeadOn()) {
            onDeadOn();

            int DeadOnTimes = DeadOnAmount();

            for (int a = 0; a < DeadOnTimes; a++) {
                this.addToBot(new DrawCardAction(magicNumber));
            }

            this.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SnipePower.POWER_ID, 1));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.hand.size();
        super.applyPowers();

        this.getMyName();

        this.rawDescription += cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}