package hermit.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.powers.Concentration;
import hermit.powers.ShadowCloakPower;
import hermit.powers.TakeAimPower;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class TakeAim extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(TakeAim.class.getSimpleName());
    public static final String IMG = makeCardPath("take_aim.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    private static final int MAGIC_NUMBER = 0;
	private static final int DO_CONC_UP = 1;

    // /STAT DECLARATION/


    public TakeAim() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        loadJokeCardImage(this, "take_aim.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasPower(TakeAimPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TakeAimPower(p, -1), -1));
        }

        if (magicNumber > 0) {
            if (!AbstractDungeon.player.hasPower(Concentration.POWER_ID))
            {
                this.addToBot(new ApplyPowerAction(p, p, new Concentration(p, -1), -1));
            }
        }
    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
			upgradeMagicNumber(DO_CONC_UP);
			rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
