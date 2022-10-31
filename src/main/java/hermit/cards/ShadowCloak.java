package hermit.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.powers.ShadowCloakPower;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class ShadowCloak extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(ShadowCloak.class.getSimpleName());
    public static final String IMG = makeCardPath("shadow_cloak.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    private static final int MAGIC_NUMBER = 6;
    private static final int UP_MAGIC = 3;

    // /STAT DECLARATION/


    public ShadowCloak() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        loadJokeCardImage(this, "shadow_cloak.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ShadowCloakPower(p, magicNumber), magicNumber));
    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MAGIC);
            initializeDescription();
        }
    }
}
