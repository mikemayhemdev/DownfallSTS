package guardian.cards;



import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.patches.AbstractCardEnum;
import guardian.powers.NextTurnGainStrengthPower;

public class ChargeUp extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ChargeUp");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/chargeup.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int BLOCK = 12;
    private static final int UPGRADE_BLOCK = 3;
    private static final int STRENGTH = 1;
    private static final int UPGRADE_STRENGTH = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public ChargeUp() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);


        this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = STRENGTH;
        this.tags.add(GuardianMod.TICK);
        this.tags.add(GuardianMod.VOLATILE);
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();

}

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(this));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));

        super.useGems(p,m);
    }

    public AbstractCard makeCopy() {
        return new ChargeUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //upgradeBlock(UPGRADE_BLOCK);
            this.cost = 1;
            this.costForTurn = 1;
            this.upgradedCost = true;

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

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean result;
        if (GuardianMod.canSpawnStasisOrb()){
            result = true;
        } else {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            result = false;
        }
        return result;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}


