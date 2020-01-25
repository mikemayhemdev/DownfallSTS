package guardian.cards;



import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.actions.RandomProtocolAction;
import guardian.patches.AbstractCardEnum;

import java.util.ArrayList;


public class MultiBeam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("MultiBeam");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/multiBeam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = -1;
    private static final int DAMAGE = 5;
    private static final int BEAMBUFF = 1;
    private static final int UPGRADE_BEAMBUFF = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS




    public MultiBeam() {

        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
this.tags.add(GuardianMod.BEAM);

        this.magicNumber = this.baseMagicNumber = BEAMBUFF;
        this.isMultiDamage = true;
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();
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
        super.use(p,m);

        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        if (p.hasRelic("Chemical X")) {
            this.energyOnUse += 2;
            p.getRelic("Chemical X").flash();
        }
        if (upgraded) this.energyOnUse++;

        for (int i = 0; i < this.energyOnUse; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new SweepingBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));

        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

        super.useGems(p,m);

    }


    public AbstractCard makeCopy() {

        return new MultiBeam();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;

            this.initializeDescription();
            upgradeMagicNumber(UPGRADE_BEAMBUFF);

        }

    }

    public void updateDescription(){

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION,true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION,true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


