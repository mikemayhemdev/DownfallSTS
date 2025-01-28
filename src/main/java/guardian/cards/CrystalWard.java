package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.powers.PolishPower;
import guardian.GuardianMod;

import static guardian.GuardianMod.makeBetaCardPath;


public class CrystalWard extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("CrystalWard");
    public static final String NAME;
    public static final String IMG_PATH = "cards/crystalWard.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int BLOCK = 4;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 2;
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


    public CrystalWard() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasPower(PolishPower.POWER_ID))) {
            this.baseBlock = (BLOCK + AbstractDungeon.player.getPower(PolishPower.POWER_ID).amount);
        } else {
            this.baseBlock = BLOCK;
        }

//this.sockets.add(GuardianMod.socketTypes.RED);

        this.exhaust = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("CrystalWard.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

    }

    public AbstractCard makeCopy() {
        return new CrystalWard();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BONUS);
        }
    }

    @Override
    public void applyPowers() {
        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasPower(PolishPower.POWER_ID))) {
            this.baseBlock = (BLOCK + AbstractDungeon.player.getPower(PolishPower.POWER_ID).amount);
        }
        super.applyPowers();
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


