package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.vfx.IronWaveEffectBlue;


public class Harden extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Harden");
    public static final String NAME;
    public static final String IMG_PATH = "cards/harden2.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 10;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 3;
    private static final int SOCKETS = 1;
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


    public Harden() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

        //this.sockets.add(GuardianMod.socketTypes.RED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        if (p != null && m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new IronWaveEffectBlue(p.hb.cX, p.hb.cY, m.hb.cX), 0.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new Harden();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeBlock(UPGRADE_BLOCK);
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


