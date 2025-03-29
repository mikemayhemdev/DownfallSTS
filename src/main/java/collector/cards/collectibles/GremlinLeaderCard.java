package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class GremlinLeaderCard extends AbstractCollectibleCard {
    public final static String ID = makeID(GremlinLeaderCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 2, 1

    public GremlinLeaderCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            makeInHand(getRandomGremlinCollectible());
        }
    }

    private AbstractCard getRandomGremlinCollectible() {
        ArrayList<String> gremlinCards = new ArrayList<>();
        gremlinCards.add(SneakyGremlinCard.ID);
        gremlinCards.add(ShieldGremlinCard.ID);
        gremlinCards.add(MadGremlinCard.ID);
        gremlinCards.add(GremlinWizardCard.ID);
        gremlinCards.add(FatGremlinCard.ID);
        AbstractCard result = CardLibrary.getCopy(Wiz.getRandomItem(gremlinCards, AbstractDungeon.cardRandomRng));
        CardModifierManager.addModifier(result, new CollectedCardMod());
        return result;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}