package guardian.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.GemFireAction;
import guardian.patches.AbstractCardEnum;
import sneckomod.SneckoMod;


public class GemFire extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GemFire");
    public static final String NAME;
    public static final String IMG_PATH = "cards/gemFire.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int DAMAGE = 10;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 5;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public GemFire() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;
        this.baseDamage = DAMAGE;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new GemFireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

    }

    public AbstractCard makeCopy() {
        return new GemFire();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
          //  upgradeDamage(UPGRADE_BONUS);
            selfRetain = true;
          //  exhaust = false;
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
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


