package guardian.cards;


import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.BraceWallopAction;
import guardian.actions.PlaceRandomCardIntoStasisAction;
import guardian.patches.AbstractCardEnum;
import guardian.vfx.BronzeOrbEffect;

import static collector.util.Wiz.applyToEnemy;
import static guardian.GuardianMod.makeBetaCardPath;


public class BronzeOrb extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("BronzeOrb");
    public static final String NAME;
    public static final String IMG_PATH = "cards/BronzeOrb.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 9;

    //TUNING CONSTANTS
    private static final int UPGRADE_DAMAGE = 3;
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
        // this.baseBlock = BLOCK;

        //this.sockets.add(GuardianMod.socketTypes.RED);
        // this.isInnate = true;
        this.exhaust = true;
        this.socketCount = SOCKETS;
        this.tags.add(GuardianMod.BEAM);
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("BronzeOrb.png"));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));

        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new BraceWallopAction((AbstractCreature)m, (AbstractCreature)p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));

        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new BronzeOrb();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            //      upgradeBlock(UPGRADE_BLOCK);
        }
    }
}

