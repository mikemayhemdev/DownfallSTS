package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.powers.*;
import guardian.relics.ModeShifterPlus;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;
import sneckomod.powers.ToxicPersonalityPower;

import static guardian.GuardianMod.makeBetaCardPath;

public class SphericShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SphericShield");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/sphereShieldSkill.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SphericShield() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.baseBlock = 10;
        this.socketCount = 0;
        exhaust = true;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("SphericShield.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ChangeStanceAction(new DefensiveMode()));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DontLeaveDefensiveModePower(AbstractDungeon.player, 1), 1));

        ModeShifterPlus modeShifterPlusInstance = new ModeShifterPlus();
        if (AbstractDungeon.player.hasRelic(ModeShifterPlus.ID)) {
        modeShifterPlusInstance.onTrigger();
        }

        if (AbstractDungeon.player.hasPower(RevengePower.POWER_ID)) {
            RevengePower revengePower =
                    (RevengePower) AbstractDungeon.player.getPower(RevengePower.POWER_ID);

            if (revengePower != null) {
                revengePower.onActivateCallR(p);
            }
        }

        if (AbstractDungeon.player.hasPower(SpikerProtocolPower.POWER_ID)) {
            SpikerProtocolPower spikerProtocolPower =
                    (SpikerProtocolPower) AbstractDungeon.player.getPower(SpikerProtocolPower.POWER_ID);

            if (spikerProtocolPower != null) {
                spikerProtocolPower.onActivateCallS(p);
            }
        }


        if (AbstractDungeon.player.hasPower(EvasiveProtocolPower.POWER_ID)) {
            EvasiveProtocolPower evasiveProtocolPower =
                    (EvasiveProtocolPower) AbstractDungeon.player.getPower(EvasiveProtocolPower.POWER_ID);

            if (evasiveProtocolPower != null) {
                evasiveProtocolPower.onActivateCallE(p);
            }


        }

    }

    public AbstractCard makeCopy() {
        return new SphericShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
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


