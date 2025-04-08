package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;
import static collector.util.Wiz.shuffleIn;

public class Darkstorm extends AbstractCollectorCard {
    public final static String ID = makeID(Darkstorm.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Darkstorm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Blightning();
        exhaust = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = new Blightning();
        makeInHand(q);
        shuffleIn(q, magicNumber);
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}