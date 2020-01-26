package guardian.cards;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

public class zzzSpikerProtocol extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("zzzSpikerProtocol");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/spikeProtocol.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int THORNS = 3;
    private static final int UPGRADE_THORNS = 3;
    private static final int DAMAGE = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public zzzSpikerProtocol() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = THORNS;
        this.secondaryM = DAMAGE;
        this.tags.add(GuardianMod.PROTOCOL);
}

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
      //  if (GuardianMod.bronzeOrbInPlay == null){
         //   AbstractDungeon.actionManager.addToBottom(new SpawnBronzeOrbAction(AbstractDungeon.player, SpawnBronzeOrbAction.powerTypes.DAMAGE, DAMAGE, 0, SpawnBronzeOrbAction.powerTypes.THORNS, this.magicNumber, 0));
       // } else {
          //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(GuardianMod.bronzeOrbInPlay, p, new BronzeOrbDamagePower(GuardianMod.bronzeOrbInPlay, DAMAGE), DAMAGE));
        //    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(GuardianMod.bronzeOrbInPlay, p, new ThornsPower(GuardianMod.bronzeOrbInPlay, this.magicNumber), this.magicNumber));


      //  }
    }

    public AbstractCard makeCopy() {
        return new zzzSpikerProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THORNS);
        }
    }



    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


