package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.PropertiesMod;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static hermit.util.Wiz.makeInHand;

public class FaceTraderCard extends AbstractCollectibleCard {
    public final static String ID = makeID(FaceTraderCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 2, 1

    public FaceTraderCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard q = SneckoMod.getRandomAnyColorCard();
            if (!q.isEthereal)
                CardModifierManager.addModifier(q, new PropertiesMod(PropertiesMod.supportedProperties.ETHEREAL, false));
            makeInHand(q);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}