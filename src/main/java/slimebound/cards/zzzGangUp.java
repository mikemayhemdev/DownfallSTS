package slimebound.cards;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.SlimeboundMod;
import slimebound.actions.GangUpAction;
import slimebound.patches.AbstractCardEnum;


public class zzzGangUp extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzGangUp";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/rollthrough.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;

    private static final int COST = -1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static boolean UpgradeCard;

    public zzzGangUp() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;}
        if (upgraded) this.energyOnUse++;
        if (p.hasRelic(ChemicalX.ID)) {
            this.energyOnUse += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        if (energyOnUse > 0) com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction(this.energyOnUse));

         if (energyOnUse > 0) AbstractDungeon.actionManager.addToBottom(new GangUpAction(this.energyOnUse -1,this.magicNumber,true));


            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BuffAttackSlimesPower(p, p, this.magicNumber), this.magicNumber));

            p.energy.use(EnergyPanel.totalCount);


        }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public AbstractCard makeCopy() {
        return new zzzGangUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            upgradeMagicNumber(1);

        }
    }
}

