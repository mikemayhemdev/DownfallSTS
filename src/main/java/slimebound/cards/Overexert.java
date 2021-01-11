package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.LoseSlimesPower;
import slimebound.powers.PotencyPower;
import sneckomod.SneckoMod;


public class Overexert extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Overexert";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/knowledgepool.png";
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Overexert() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 2;

        //this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotencyPower(p, p, 4), 4));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseSlimesPower(p, p, this.magicNumber), this.magicNumber));

        if (upgraded) addToBot(new CommandAction());
        if (upgraded) addToBot(new CommandAction());
        checkMinionMaster();
    }

    public AbstractCard makeCopy() {
        return new Overexert();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }
    }
}

