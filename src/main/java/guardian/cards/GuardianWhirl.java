package guardian.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import hermit.cards.AbstractDynamicCard;

import static guardian.GuardianMod.makeBetaCardPath;

public class GuardianWhirl extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GuardianWhirl");
    public static final String IMG_PATH = "cards/guardianwhirl.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 2;
    private static final int DAMAGE = 4;
    private static final int MULTICOUNT = 2;
    private static final int SOCKETS = 0;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public GuardianWhirl() {
        super(ID, cardStrings.NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MULTICOUNT;
        this.isMultiDamage = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("guardianwhirl.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));

        for (int i = 0; i < 2; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }

        if (p.currentBlock >= 16) {
            for (int i = 0; i < this.magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.05F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            }
        }

        this.useGems(p, m);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.currentBlock >= 16) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            //upgradeMagicNumber(1);
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (this.socketCount > 0) {
            this.rawDescription = upgraded && UPGRADED_DESCRIPTION != null ? updateGemDescription(UPGRADED_DESCRIPTION, true) : updateGemDescription(DESCRIPTION, true);
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new GuardianWhirl();
    }
}
