package charbosses.bosses.Silent;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.cards.green.EnDefendGreen;
import charbosses.cards.green.EnNeutralize;
import charbosses.cards.green.EnStrikeGreen;
import charbosses.cards.green.EnSurvivor;
import charbosses.cards.red.*;
import charbosses.relics.*;

public class ArchetypeBaseSilent extends AbstractBossDeckArchetype {

    public ArchetypeBaseSilent(String id, String loggerName) {
        super(id, "Silent", loggerName);
    }

    @Override
    public void initialize() {
        //Define Class Starter Deck
        for (int i = 0; i < 5; ++i) {
            addToStarterDeck(new EnStrikeGreen());
        }
        for (int i = 0; i < 4; ++i) {
            addToStarterDeck(new EnDefendGreen());
        }
        addToStarterDeck(new EnSurvivor());
        addToStarterDeck(new EnNeutralize());

        //Define Starting Relic
        starterRelic = new CBR_SnakeRing();

        //Define Class Global Cards - These can be added to any Archetype of the boss
        addCardToList(new EnArmaments(), CardBenefitType.GLOBAL);
        addCardToList(new EnIronWave(), CardBenefitType.GLOBAL);
        addCardToList(new EnAnger(), CardBenefitType.GLOBAL);
        addCardToList(new EnSentinel(), CardBenefitType.GLOBAL);
        addCardToList(new EnCleave(), CardBenefitType.GLOBAL);
        addCardToList(new EnSeeingRed(), CardBenefitType.GLOBAL);
        addCardToList(new EnGhostlyArmor(), CardBenefitType.GLOBAL);
        addCardToList(new EnFlex(), CardBenefitType.GLOBAL);
        addCardToList(new EnGhostlyArmor(), CardBenefitType.GLOBAL);

        //Define Class-specific Relics that can appear in the pool
        addRelicToList(new CBR_MagicFlower(), CardBenefitType.GLOBAL);
        addRelicToList(new CBR_RedSkull(), CardBenefitType.GLOBAL);
        addRelicToList(new CBR_StrikeDummy(), CardBenefitType.GLOBAL);

    }

}