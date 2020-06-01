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
import guardian.actions.PlaceRandomCardIntoStasisAction;
import guardian.patches.AbstractCardEnum;
import guardian.vfx.BronzeOrbEffect;


public class BronzeOrb extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("BronzeOrb");
    public static final String NAME;
    public static final String IMG_PATH = "cards/BronzeOrb.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int DAMAGE = 5;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 2;
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


    public BronzeOrb() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;

        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.isInnate = true;
        this.exhaust = true;
        this.socketCount = SOCKETS;
        this.tags.add(GuardianMod.BEAM);
        updateDescription();
        loadGemMisc();

    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        return tmp + calculateBeamDamage();
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, float tmp) {
        return tmp + calculateBeamDamage();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));

        AbstractDungeon.actionManager.addToBottom(new PlaceRandomCardIntoStasisAction(1));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new BronzeOrb();
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


