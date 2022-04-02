package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.OffclassHelper;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SoulDraw extends AbstractSneckoCard {

    public final static String ID = makeID("SoulDraw");

    public SoulDraw() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = OffclassHelper.getXRandomOffclassCards(magicNumber);
        for (AbstractCard c : cards) {
            makeInHand(c);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}