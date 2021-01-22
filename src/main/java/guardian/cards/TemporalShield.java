package guardian.cards;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

public class TemporalShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TemporalShield");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/temporalShield.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    //TUNING CONSTANTS
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 2;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    //END TUNING CONSTANTS

    public void triggerOnGlowCheck() {
        this.glowColor = GuardianMod.isStasisOrbInPlay() ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    private int lastKnownStasisCount = 0;

    public TemporalShield() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = 1;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (GuardianMod.isStasisOrbInPlay()) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.magicNumber));
        }
        super.use(p, m);
        this.useGems(p, m);

    }

    public AbstractCard makeCopy() {
        return new TemporalShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(1);
            updateDescription();
        }
    }

    public void updateDescription() {
        this.rawDescription = upgraded ? UPGRADED_DESCRIPTION : DESCRIPTION;
        this.initializeDescription();
    }
}


