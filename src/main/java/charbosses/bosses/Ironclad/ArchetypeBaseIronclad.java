package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.cards.red.*;
import charbosses.relics.*;

public class ArchetypeBaseIronclad extends AbstractBossDeckArchetype {

    public ArchetypeBaseIronclad(String id, String loggerName) {
        super(id, "Ironclad", loggerName);

    }

    @Override
    public void initialize() {
        //Define Class Starter Deck
        for (int i = 0; i < 5; ++i) {
            addToStarterDeck(new EnStrikeRed());
        }
        for (int i = 0; i < 4; ++i) {
            addToStarterDeck(new EnDefendRed());
        }
        addToStarterDeck(new EnBash());

        //Define Starting Relic
        starterRelic = new CBR_BurningBlood();

        //Define Class Global Cards - These can be added to any Archetype of the boss
        addCardToList(new EnArmaments(), CardBenefitType.GLOBAL);
        addCardToList(new EnIronWave(), CardBenefitType.GLOBAL);
        addCardToList(new EnAnger(), CardBenefitType.GLOBAL);
        addCardToList(new EnSentinel(), CardBenefitType.GLOBAL);
        addCardToList(new EnCleave(), CardBenefitType.GLOBAL);
        addCardToList(new EnSeeingRed(), CardBenefitType.GLOBAL);
        addCardToList(new EnGhostlyArmor(), CardBenefitType.GLOBAL);
        addCardToList(new EnFlex(), CardBenefitType.GLOBAL);

        //Define Class-specific Relics that can appear in the pool
        addRelicToList(new CBR_MagicFlower(), CardBenefitType.GLOBAL);
        addRelicToList(new CBR_RedSkull(), CardBenefitType.GLOBAL);

        addRelicToList(new CBR_MarkOfPain(), CardBenefitType.ENERGY);

    }

}