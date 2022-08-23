//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package automaton.cards.encodedcards;


import automaton.cards.AbstractBronzeCard;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.RetainCardMod;
import downfall.util.CardIgnore;

@CardIgnore
public class EncodedEntrench extends AbstractBronzeCard {
    public static final String ID = "bronze:EncodedEntrench";
    private static final CardStrings cardStrings;

    public EncodedEntrench() {
        super("bronze:EncodedEntrench", cardStrings.NAME, "red/skill/entrench", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DoubleYourBlockAction(p));
    }

    public AbstractCard makeCopy() {
        return new EncodedEntrench();
    }

    public void upgrade() {
        this.upgradeBaseCost(1);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("bronze:EncodedEntrench");
    }
}
