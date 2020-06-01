package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.CoordinateAction;
import slimebound.patches.AbstractCardEnum;


public class Teamwork extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Teamwork";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/coordinatedstrike.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardStrings cardStrings;
    private static final int COST = -1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Teamwork() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        baseBlock = 5;
        //this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new CoordinateAction(p, m, this.magicNumber, this.freeToPlayOnce, this.energyOnUse, block, upgraded));

    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}


