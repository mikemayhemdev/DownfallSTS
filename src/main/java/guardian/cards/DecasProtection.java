package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class DecasProtection extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("DecasProtection");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/ancientProtection.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 2;

    //TUNING CONSTANTS
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public DecasProtection() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));

        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();

        if (upgraded) c.modifyCostForCombat(-1);
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c, true));


    }

    public AbstractCard makeCopy() {
        return new DecasProtection();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
        }
        this.initializeDescription();
    }
}


