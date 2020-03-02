package charbosses.bosses.Defect;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.red.*;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_Orichalcum;
import charbosses.relics.CBR_Vajra;
import charbosses.relics.CBR_WarPaint;
import charbosses.relics.EventRelics.CBR_Serpent;

public class ArchetypeAct1 extends ArchetypeBaseIronclad {

    public ArchetypeAct1() {
        super("DF_ARCHETYPE_", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //1 Strike 1 Defend Removed

        //7 Cards Added, 3 Upgrades:



        //Auto-Shields
        //Core Surge



        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        //data disk
        //oddly smooth stone
        //transmogrifier

        /////   CARDS   /////

        //Turn 1
        //Zap
        //Defend
        //Dualcast

        //Turn 2
        //Cold Snap
        //Leap+
        //Strike

        //Turn 3
        //Ball Lightning+
        //Defend
        //Defend

        //Turn 4
        //Charge Battery
        //Rip and Tear
        //Strike

        //Turn 5
        //Rebound
        //Streamline+
        //Strike




    }


}