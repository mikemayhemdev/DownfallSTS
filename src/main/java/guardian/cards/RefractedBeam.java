package guardian.cards;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.ModifyMagicNumberAction;
import guardian.patches.AbstractCardEnum;
import guardian.vfx.SmallLaserEffectColored;


public class RefractedBeam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("RefractedBeam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/refractedBeam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
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

    public RefractedBeam() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);
        this.tags.add(GuardianMod.BEAM);


        this.magicNumber = this.baseMagicNumber = MULTICOUNT;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
       // exhaust = true;
        //this.sockets.add(GuardianMod.socketTypes.RED);
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
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            float randoX = MathUtils.random(-80, 80);
            float randoY = MathUtils.random(-80, 80);

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(p.hb.cX, p.hb.cY, m.hb.cX + (randoX * Settings.scale), m.hb.cY + (randoY * Settings.scale), Color.CYAN), 0.15F));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        }

        addToBot(new ModifyMagicNumberAction(this.uuid, -1));
    }

    public AbstractCard makeCopy() {
        return new RefractedBeam();
    }

    public void upgrade() {
        upgradeMagicNumber(1);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
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


