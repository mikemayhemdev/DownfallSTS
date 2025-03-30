package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHandTop;

public class GreenpyreLocus extends AbstractCollectorCard {
    public final static String ID = makeID(GreenpyreLocus.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 1, 1

    public GreenpyreLocus() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> allCollecteds = Wiz.getCardsMatchingPredicate(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE && c.rarity != CardRarity.SPECIAL && !c.hasTag(CardTags.HEALING), true);
        ArrayList<AbstractCard> selectionChoices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            selectionChoices.add(allCollecteds.remove(AbstractDungeon.cardRandomRng.random(allCollecteds.size() - 1)));
        }
        addToBot(new SelectCardsCenteredAction(selectionChoices, 1, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard tar = cards.get(0).makeCopy();
            CardModifierManager.addModifier(tar, new CollectedCardMod());
            makeInHandTop(tar);
            for (int i = 0; i < magicNumber; i++) {
                CollectorCollection.combatCollection.addToRandomSpot(tar.makeStatEquivalentCopy());
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}