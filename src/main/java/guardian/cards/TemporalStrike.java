package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class TemporalStrike extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TemporalStrike");
    public static final String NAME;
    public static final String IMG_PATH = "cards/temporalStrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 5;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 1;
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


    public TemporalStrike() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();


        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.updateDescription();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (GuardianMod.isStasisOrbInPlay()) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        super.use(p, m);
        this.useGems(p, m);

    }

    public void triggerOnGlowCheck() {
        this.glowColor = GuardianMod.isStasisOrbInPlay() ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public AbstractCard makeCopy() {
        return new TemporalStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (this.socketCount < 4) {
                this.socketCount++;
                this.saveGemMisc();
            }
            upgradeDamage(UPGRADE_DAMAGE);
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


