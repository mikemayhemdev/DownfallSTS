package collector.cards;

import collector.CollectorCollection;
import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import hermit.util.Wiz;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;

public class GreenpyreLocus extends AbstractCollectorCard {
    public final static String ID = makeID(GreenpyreLocus.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 1, 1

    public GreenpyreLocus() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> allCollecteds = Wiz.getCardsMatchingPredicate(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE, true);
        ArrayList<AbstractCard> selectionChoices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            selectionChoices.add(allCollecteds.remove(AbstractDungeon.cardRandomRng.random(allCollecteds.size() - 1)));
        }
        addToBot(new SelectCardsCenteredAction(selectionChoices, 1, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (int i = 0; i < magicNumber; i++) {
                CollectorCollection.combatCollection.addToRandomSpot(cards.get(0).makeCopy());
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}