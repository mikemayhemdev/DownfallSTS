package guardian.cards;



import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.DiscardPileToStasisAction;
import guardian.actions.PlaceTopCardIntoStasisAction;
import guardian.patches.AbstractCardEnum;

public class Recover extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Recover");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/recovery.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int CARDS = 1;
    private static final int UPGRADEBLOCK = 3;
    private static final int BLOCK = 5;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public Recover() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseBlock = BLOCK;
this.baseMagicNumber = this.magicNumber = CARDS;
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (AbstractDungeon.player.discardPile.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new DiscardPileToStasisAction(this.magicNumber));
        }

        super.useGems(p,m);
    }

    public AbstractCard makeCopy() {
        return new Recover();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADEBLOCK);
        }
    }

    public void updateDescription(){

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION,true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION,true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


