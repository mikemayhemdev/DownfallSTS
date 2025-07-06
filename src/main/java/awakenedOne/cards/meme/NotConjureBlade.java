package awakenedOne.cards.meme;

import awakenedOne.actions.ConjureBladeSpellPileAction;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ConjureBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

@CardIgnore
@NoCompendium

public class NotConjureBlade extends ConjureBlade {
    public static final String ID = "awakened:NotConjureBlade";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "purple/skill/conjure_blade";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = -1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ConjureBlade");
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


//    public NotConjureBlade() {
//        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.PURPLE, RARITY, TARGET);
//        this.cardsToPreview = new Expunger();
//        this.exhaust = true;
//        this.tags.add(AwakenedOneMod.DELVE);
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            this.addToBot(new ConjureBladeSpellPileAction(p, this.freeToPlayOnce, this.energyOnUse + 1));
        } else {
            this.addToBot(new ConjureBladeSpellPileAction(p, this.freeToPlayOnce, this.energyOnUse));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NotConjureBlade();
    }

}