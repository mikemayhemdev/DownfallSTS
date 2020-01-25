package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class FierceBash extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("FierceBash");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/fierceBash.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 3;
    private static final int DAMAGE = 30;
    private static final int UPGRADE_BONUS = 5;
    private static final int DAMAGEPERTURNINSTASIS = 3;
    private static final int UPGRADE_DAMAGEPERTURNINSTASIS = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public int turnsInStasis = 0;

    public void stasisBonus(){
        this.upgradeDamage(this.magicNumber);
    }

    public FierceBash() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = DAMAGEPERTURNINSTASIS;
        //this.sockets.add(GuardianMod.socketTypes.RED);
this.tags.add(GuardianMod.TICK);
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();

    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        this.useGems(p, m);
    }


    public AbstractCard makeCopy() {
        return new FierceBash();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
            upgradeMagicNumber(UPGRADE_DAMAGEPERTURNINSTASIS);
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


