package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.socketTypes.CRIMSON;
import static guardian.GuardianMod.socketTypes.CYAN;


public class Gem_Crimson extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Gem_Crimson");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/gemCrimson.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int VULN = 1;  //HARDCODED IN DESCRIPTION
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    //GEMS ALWAYS need entries added to OnSave, OnLoad, updateGemDescription, render methods in AbstractGuardianCard!

    public Gem_Crimson() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.tags.add(GuardianMod.GEM);
        this.thisGemsType = CRIMSON;
}

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        super.use(p,m);
        gemEffect(p,m);
    }

    public static void gemEffect(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, VULN, false), VULN));

            }
        }
    }

    public AbstractCard makeCopy() {
        return new Gem_Crimson();
    }

    public void upgrade() {
    }



    public boolean canUpgrade() {
        return false;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


