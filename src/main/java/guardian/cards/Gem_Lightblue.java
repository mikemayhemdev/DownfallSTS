package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.LoseThornsPower;
import sneckomod.SneckoMod;

import static guardian.GuardianMod.socketTypes.LIGHTBLUE;
import static guardian.GuardianMod.socketTypes.LIGHTBLUE;


public class Gem_Lightblue extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Gem_Lightblue");
    public static final String NAME;
    public static final String IMG_PATH = "cards/gemLightblue.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int Thorns = 1;  //HARDCODED IN DESCRIPTION

    //TUNING CONSTANTS
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    //GEMS ALWAYS need entries added to OnSave, OnLoad, updateGemDescription, render methods in AbstractGuardianCard!

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Gem_Lightblue() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = Thorns;
        this.tags.add(GuardianMod.GEM);
        this.thisGemsType = LIGHTBLUE;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(CardTags.HEALING);
    }

    public static void gemEffect(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, Thorns), Thorns));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseThornsPower(p, Thorns), Thorns));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        gemEffect(p, m);
    }

    public AbstractCard makeCopy() {
        return new Gem_Lightblue();
    }

    public void upgrade() {

    }

    public boolean canUpgrade() {
        return false;
    }
}


