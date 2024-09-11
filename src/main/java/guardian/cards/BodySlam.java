package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;


public class BodySlam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("BodySlam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/BodySlam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int SOCKETS = 0;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public BodySlam() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.baseBlock = 5;
        this.baseDamage = 0;
        this.socketCount = SOCKETS;
        this.rawDescription = DESCRIPTION;
        initializeDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("BodySlam.png"));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        this.baseDamage = p.currentBlock + this.block;
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, block));
        addToBot(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = AbstractDungeon.player.currentBlock + this.block;
        super.applyPowers();

        this.rawDescription = DESCRIPTION;
        this.rawDescription += UPGRADED_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = DESCRIPTION;
        this.rawDescription += UPGRADED_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}


