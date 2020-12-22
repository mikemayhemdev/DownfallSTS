package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.blue.*;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.SteamBarrier;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1StreamlineNewAge extends ArchetypeBaseDefect {

    public ArchetypeAct1StreamlineNewAge() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
        bossMechanicName = bossMechanicString.DIALOG[0];
        bossMechanicDesc = bossMechanicString.DIALOG[1];
    }

    private int steamBarrierCasts;

    public void initialize() {
        steamBarrierCasts = 0;
        addRelic(new CBR_NeowsBlessing());

        //data disk
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_ClockworkSouvenir());
        addRelic(new CBR_LetterOpener());
      //  addRelic(new CBR_Abacus());
        // addRelic(new CBR_Transmogrifier());  //Could be something else, no strong lean in any direction
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnTurbo(), false);
                    addToList(cardsList, new EnEquilibrium(), extraUpgrades);
                    addToList(cardsList, new EnBuffer(), false);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnSteamBarrier(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnGeneticAlgorithm(9), false);
                    turn++;
                    steamBarrierCasts++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnTurbo(), true);
                    addToList(cardsList, new EnSunder(), true);
                    addToList(cardsList, new EnAutoShields(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnCoreSurge(), false);
                    addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }

        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnTurbo(), true);
                    addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    addToList(cardsList, new EnEquilibrium(), extraUpgrades);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnSteamBarrier(steamBarrierCasts), false);
                    steamBarrierCasts++;
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnTurbo(), true);
                    addToList(cardsList, new EnSunder(), true);
                    addToList(cardsList, new EnAutoShields(), extraUpgrades);
                    turn = 0;
                    break;

            }
        }

        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SymbioticVirus());
    }

}