package guardian.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.vfx.SmallLaserEffectColored;
import sneckomod.SneckoMod;


public class CrystalBeam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("CrystalBeam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/crystalBeam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 10;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 4;
    private static final int DAMAGEPERGEM = 2;
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

    public CrystalBeam() {

        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = DAMAGEPERGEM;
        this.tags.add(GuardianMod.BEAM);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

        this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public static int countCards() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (((AbstractGuardianCard) c).sockets.size() > 0)
                    count += ((AbstractGuardianCard) c).sockets.size();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (((AbstractGuardianCard) c).sockets.size() > 0)
                    count += ((AbstractGuardianCard) c).sockets.size();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (((AbstractGuardianCard) c).sockets.size() > 0)
                    count += ((AbstractGuardianCard) c).sockets.size();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractGuardianCard) {
                if (((AbstractGuardianCard) c).sockets.size() > 0)
                    count += ((AbstractGuardianCard) c).sockets.size();
            }
        }
        return count;
    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        int cards = countCards();
        bonus = cards * this.magicNumber;
        return tmp + bonus + calculateBeamDamage();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, Color.BLUE), 0.3F));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

    }

    public AbstractCard makeCopy() {

        return new CrystalBeam();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(UPGRADE_BONUS);

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


