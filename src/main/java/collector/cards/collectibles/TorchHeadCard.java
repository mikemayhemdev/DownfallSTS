package collector.cards.collectibles;

import collector.cards.BindingCall;
import collector.cards.MastersCall;
import collector.cards.ProtectingCall;
import collector.cards.RagingCall;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class TorchHeadCard extends AbstractCollectibleCard {
    public final static String ID = makeID(TorchHeadCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public TorchHeadCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard toAdd = getRandomTorchheadCard();
        toAdd.setCostForTurn(0);
        makeInHand(toAdd);
    }

    private AbstractCard getRandomTorchheadCard() {
        ArrayList<String> torchheadCards = new ArrayList<>();
        torchheadCards.add(BindingCall.ID);
        torchheadCards.add(MastersCall.ID);
        torchheadCards.add(ProtectingCall.ID);
        torchheadCards.add(RagingCall.ID);
        return CardLibrary.getCopy(Wiz.getRandomItem(torchheadCards));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}