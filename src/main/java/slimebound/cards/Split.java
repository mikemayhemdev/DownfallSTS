package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;
import slimebound.SlimeboundMod;


import slimebound.actions.CommandCidAction;
import slimebound.actions.CommandPikeAction;
import slimebound.actions.EnergyToCidAction;
import slimebound.actions.EnergyToPikeAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.SlimeHelper;


public class Split extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Split";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/split.png");
    private static final CardStrings cardStrings;
    public static final String[] EXTENDED_DESCRIPTION;

    public Split() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.BASIC, CardTarget.NONE);
        SlimeboundMod.loadJokeCardImage(this, "Split.png");
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (SlimeHelper.GetCidEnergy() >= SlimeHelper.GetPikeEnergy()){
            addToBot(new EnergyToCidAction(magicNumber));
            addToBot(new CommandCidAction());
        } else {
            addToBot(new EnergyToPikeAction(magicNumber));
            addToBot(new CommandPikeAction());
        }

    }


    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:SplitPike": {

                break;
            }
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}

