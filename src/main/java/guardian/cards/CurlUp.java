package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;

public class CurlUp extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("CurlUp");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/curlUp.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    //TUNING CONSTANTS
    private static final int BLOCK = 10;
    private static final int UPGRADE_BONUS = 3;
    private static final int MULTICOUNT = 0;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public CurlUp() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);


        this.baseBlock = BLOCK;
        this.multihit = MULTICOUNT;
        this.socketCount = SOCKETS;
        baseMagicNumber = magicNumber = 10;
        updateDescription();
        loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        //if (upgraded) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (p.stance instanceof DefensiveMode) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        } else {
            brace(magicNumber);
        }
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new CurlUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            upgradeBlock(3);

            this.updateDescription();
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


