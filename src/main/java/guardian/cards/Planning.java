package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.PlaceTopCardIntoStasisAction;
import guardian.patches.AbstractCardEnum;
import hermit.actions.ReduceDebuffsAction;

import static guardian.GuardianMod.makeBetaCardPath;

public class Planning extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Planning");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/planning.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    private static final int CARDS = 1;
    private static final int UPGRADE_CARDS = 1;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public Planning() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        baseBlock = 12;
        this.baseMagicNumber = this.magicNumber = CARDS;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("Planning.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new ReduceDebuffsAction(AbstractDungeon.player, this.magicNumber));
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new Planning();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


