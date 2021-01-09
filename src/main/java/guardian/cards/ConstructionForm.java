package guardian.cards;


import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.ConstructModePower;

public class ConstructionForm extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ConstructionForm");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/constructionForm.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 3;

    //TUNING CONSTANTS
    private static final int TURNS = 2;
    private static final int UPGRADE_TURNS = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ConstructionForm() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = TURNS;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        isEthereal = true;

        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConstructModePower(p, 1), 1));

    }

    public AbstractCard makeCopy() {
        return new ConstructionForm();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
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


