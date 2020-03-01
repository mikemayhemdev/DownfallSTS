package charbosses.bosses.Ironclad;

import charbosses.cards.curses.EnDoubt;
import charbosses.cards.red.*;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_Orichalcum;
import charbosses.relics.CBR_Vajra;
import charbosses.relics.CBR_WarPaint;
import charbosses.relics.EventRelics.CBR_Serpent;

public class ArchetypeAct2Strength extends ArchetypeBaseIronclad {

    public ArchetypeAct2Strength() {
        super("IC_STRIKE_ARCHETYPE", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act: 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Make the total divisible by 3 - Shuffle should not occur on a partial hand
        //Should be given 2 relics and an Event relic (simulate what the Event did)

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //1 Strike 1 Defend Removed

        //6 Cards Added:
        //Metallicize
        //Perfected Strike
        //Twin Strike
        //Ghostly Armor
        //Inflame
        //Wild Strike

        //Metallicize and Ghostly Armor upgraded

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        addRelic(new CBR_Vajra());
        addRelic(new CBR_WarPaint());   //Upgrade 2 Defends
        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_Serpent());   //Money used to buy Orichalcum

        /////   CARDS   /////

        //Turn 1
        addToDeck(new EnBash(), false);
        addToDeck(new EnMetallicize(), false);
        addToDeck(new EnDoubt(), false);

        //Turn 2
        addToDeck(new EnDefendRed(), true);
        addToDeck(new EnDefendRed(), false);
        addToDeck(new EnStrikeRed(), false);

        //Turn 3
        addToDeck(new EnPerfectedStrike(), false);
        addToDeck(new EnDefendRed(), true);
        addToDeck(new EnStrikeRed(), false);

        //Turn 4
        addToDeck(new EnTwinStrike(), false);
        addToDeck(new EnGhostlyArmor(), true);
        addToDeck(new EnStrikeRed(), false);

        //Turn 5
        addToDeck(new EnStrikeRed(), false);
        addToDeck(new EnInflame(), false);
        addToDeck(new EnWildStrike(), true);




    }


}