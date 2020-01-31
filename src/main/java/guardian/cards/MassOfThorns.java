package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.LoseThornsPower;

public class MassOfThorns extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("MassOfThorns");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/spikeProtocol.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    //TUNING CONSTANTS
    private static final int THORNS = 10;
    private static final int UPGRADE_THORNS = 4;
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

    public MassOfThorns() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = THORNS;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseThornsPower(p, this.magicNumber), this.magicNumber));
        if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
            this.exhaust = true;
        }
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new MassOfThorns();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THORNS);

        }
    }
}


