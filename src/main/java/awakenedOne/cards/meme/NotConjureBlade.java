//package awakenedOne.cards.meme;
//
//import awakenedOne.AwakenedOneMod;
//import awakenedOne.actions.ConjureBladeSpellPileAction;
//import awakenedOne.cards.AbstractAwakenedCard;
//import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
//import charbosses.cards.AbstractBossCard;
//import charbosses.cards.purple.EnConjurBlade;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.tempCards.Expunger;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static awakenedOne.AwakenedOneMod.*;
//
//@NoCompendium
//
//public class NotConjureBlade extends AbstractBossCard {
//
//    public static final String ID = "awakened:NotConjureBlade";
//    private static final CardStrings cardStrings;
//
//    static {
//        cardStrings = CardCrawlGame.languagePack.getCardStrings("ConjureBlade");
//    }
//
//    public NotConjureBlade() {
//        super(ID, cardStrings.NAME, "purple/skill/conjure_blade", -1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
//        cardsToPreview = new Expunger();
//        this.exhaust = true;
//        this.tags.add(AwakenedOneMod.DELVE);
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        if (this.upgraded) {
//            this.addToBot(new ConjureBladeSpellPileAction(p, this.freeToPlayOnce, this.energyOnUse + 1));
//        } else {
//            this.addToBot(new ConjureBladeSpellPileAction(p, this.freeToPlayOnce, this.energyOnUse));
//        }
//    }
//
//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            this.upgradeName();
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            this.initializeDescription();
//        }
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new NotConjureBlade();
//    }
//
//}