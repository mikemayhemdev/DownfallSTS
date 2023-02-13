package hermit.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.util.Wiz;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class CalledShot extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(CalledShot.class.getSimpleName());
    public static final String IMG = makeCardPath("called_shot.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 0;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/

    public CalledShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        loadJokeCardImage(this, "called_shot.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), EnumPatch.HERMIT_GUN2));
        if (deadOnThisTurn.size() >= 2 && deadOnThisTurn.get(deadOnThisTurn.size() - 2).equals(true))
            Wiz.atb(new DrawCardAction(1));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (!deadOnThisTurn.isEmpty() && deadOnThisTurn.get(deadOnThisTurn.size() - 1).equals(true))
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            upgradeDamage(UPGRADE_PLUS_DMG);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}