package collector.cards.collectibles;

import collector.cards.BindingCall;
import collector.cards.ProtectingCall;
import collector.cards.RagingCall;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class TorchHeadCard extends AbstractCollectibleCard {
    public final static String ID = makeID(TorchHeadCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 
    private ArrayList<AbstractCard> dupeListForPrev = new ArrayList<>();
    private float rotationTimer;
    private int previewIndex;

    public TorchHeadCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard toAdd = getRandomTorchheadCard();
        toAdd.setCostForTurn(0);
        makeInHand(toAdd);
    }

    private AbstractCard getRandomTorchheadCard() {
        ArrayList<String> torchheadCards = new ArrayList<>();
        torchheadCards.add(BindingCall.ID);
        torchheadCards.add(ProtectingCall.ID);
        torchheadCards.add(RagingCall.ID);
        return CardLibrary.getCopy(Wiz.getRandomItem(torchheadCards));
    }

    public void upp() {
        upgradeBaseCost(0);
    }

    private void add_cards(){
        dupeListForPrev.add(new BindingCall());
        dupeListForPrev.add(new ProtectingCall());
        dupeListForPrev.add(new RagingCall());
    }

    @Override
    public void update() {
        super.update();
        if (dupeListForPrev.isEmpty()) {
            add_cards();
        }
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (dupeListForPrev.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = dupeListForPrev.get(previewIndex);
                }
                if (previewIndex == dupeListForPrev.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

}