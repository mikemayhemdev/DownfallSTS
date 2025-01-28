package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.PolyBeamAction;
import guardian.patches.AbstractCardEnum;
import hermit.patches.EnumPatch;

import static guardian.GuardianMod.makeBetaCardPath;


public class PolyBeam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PolyBeam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/polybeam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 2;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 1;
    private static final int MULTICOUNT = 4;
    private static final int UPGRADE_MULTICOUNT = 1;
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


    public PolyBeam() {

        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MULTICOUNT;

        this.tags.add(GuardianMod.MULTIHIT);
        this.tags.add(GuardianMod.BEAM);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("PolyBeam.png"));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        for(int i=0; i<this.magicNumber; i++) {
            addToBot(new PolyBeamAction(this));
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        }
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {

        return new PolyBeam();

    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        return tmp + calculateBeamDamage();
    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTICOUNT);

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


