package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimesplosionAction;
import slimebound.patches.AbstractCardEnum;


public class zzzSlimesplosion extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LivingWall";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slimewave.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = -1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public zzzSlimesplosion() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.magicNumber = this.baseMagicNumber = 4;
        //this.baseBlock = 2;
        this.poison = 2;

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new SlimesplosionAction(p, this.magicNumber, this.poison, this.freeToPlayOnce, this.energyOnUse));

    }

    public AbstractCard makeCopy() {

        return new zzzSlimesplosion();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeMagicNumber(1);
            this.poison++;

            //upgradeBlock(1);

        }

    }
}


