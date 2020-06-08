package slimebound.cards;


import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.DuplicatedFormEnergyPower;
import slimebound.powers.DuplicatedFormNoHealPower;
import slimebound.powers.DuplicatedFormPower;
import slimebound.powers.FirmFortitudePower;
import slimebound.vfx.SlimeDripsEffect;


public class DuplicatedForm extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DuplicatedForm";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/duplicatedform.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 3;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;
    private static int baseHealthCost = 15;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public DuplicatedForm() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = baseHealthCost;
        tags.add(BaseModCardTags.FORM);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        int currentHealth = AbstractDungeon.player.currentHealth;
        int healthCost = baseHealthCost;

        if (AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)) {
            healthCost = 1;
        }

        if (TempHPField.tempHp.get(AbstractDungeon.player) != null)
            currentHealth += TempHPField.tempHp.get(AbstractDungeon.player);

        if (!canUse) {
            return false;
        }
        if (AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {
            return true;
        }

        if (AbstractDungeon.player.hasPower(FirmFortitudePower.POWER_ID)) {
            if (((FirmFortitudePower) AbstractDungeon.player.getPower(FirmFortitudePower.POWER_ID)).amount2 > 0) {
                return true;
            }
        }
        if (currentHealth <= healthCost) {

            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return true;
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.effectsQueue.add(new SlimeDripsEffect(p.hb.cX, p.hb.cY, 0));
        AbstractDungeon.effectsQueue.add(new SlimeDripsEffect(p.hb.cX, p.hb.cY, 0));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IntenseZoomEffect(p.hb.cX, p.hb.cY, false), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DuplicatedFormPower(p, p, 1), 1));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DuplicatedFormNoHealPower(p, p,  p.maxHealth / 2),  p.maxHealth / 2));

        int stack = 1;
        //if (upgraded) stack++;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DuplicatedFormEnergyPower(p, p, stack), stack));


        int MaxHPActuallyLost = baseHealthCost;
        if (AbstractDungeon.player.maxHealth <= baseHealthCost) {
            MaxHPActuallyLost = AbstractDungeon.player.maxHealth - 1;
        }

        if (MaxHPActuallyLost > 0)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DuplicatedFormNoHealPower(AbstractDungeon.player, AbstractDungeon.player, MaxHPActuallyLost), MaxHPActuallyLost));
    }

    public AbstractCard makeCopy() {
        return new DuplicatedForm();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();


        }
    }
}

