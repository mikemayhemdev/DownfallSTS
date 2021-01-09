package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;


public class RollAttack extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("RollAttack");
    public static final String NAME;
    public static final String IMG_PATH = "cards/rollAttack.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 16;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 4;
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


    public RollAttack() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;

        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        this.isMultiDamage = true;
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("RollAttack.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
            if (p.stance instanceof DefensiveMode) {
            //this.isMultiDamage = true;
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else {
            //this.isMultiDamage = false;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            brace(8);
        }
        this.useGems(p, m);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDungeon.player.stance instanceof DefensiveMode ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public AbstractCard makeCopy() {
        return new RollAttack();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
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


