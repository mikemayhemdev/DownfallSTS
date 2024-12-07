package sneckomod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.PropertiesMod;
import sneckomod.OffclassHelper;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SoulDraw extends AbstractSneckoCard {

    public final static String ID = makeID("SoulDraw");

    public SoulDraw() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "SoulDraw.png");
    }


    // coffee blast reprint because
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> cards = OffclassHelper.getXRandomOffclassCards(magicNumber);
        for (AbstractCard c : cards) {
            if (!c.selfRetain) {
                CardModifierManager.addModifier(c, new PropertiesMod(PropertiesMod.supportedProperties.RETAIN, false));
            }
            makeInHand(c);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}