package guardian.cards;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.stances.DefensiveMode;
import sneckomod.SneckoMod;

import static guardian.GuardianMod.makeBetaCardPath;


public class GuardianWhirl extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GuardianWhirl");
    public static final String NAME;
    public static final String IMG_PATH = "cards/guardianwhirl.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 4;

    //TUNING CONSTANTS
    private static final int MULTICOUNT = 4;
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

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.stance instanceof DefensiveMode) {
            return super.canUse(p, m);
        } else {
            return false;
        }
    }

    public GuardianWhirl() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);

        //this.sockets.add(GuardianMod.socketTypes.RED);

       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.magicNumber = this.baseMagicNumber = MULTICOUNT;
        this.isMultiDamage = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("guardianwhirl.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));

        }
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new GuardianWhirl();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);

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


