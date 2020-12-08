package charbosses.bosses.Ironclad;

import charbosses.cards.colorless.EnBite;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.red.*;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2Strength extends ArchetypeBaseIronclad {

    public ArchetypeAct2Strength() {
        super("IC_STRENGTH_ARCHETYPE", "Strength");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck for act 1 plus ~3 per act beyond it
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //2 Strike 2 Defend Removed

        //11 Cards Added, 4 Upgrades:
        //Demon Form
        //Seeing Red
        //Inflame+
        //Heavy Blade
        //Armaments+
        //Twin Strike
        //Iron Wave+
        //Iron Wave
        //Flame Barrier
        //Clothesline+

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        //addRelic(new CBR_Girya(2));
        addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_Torii());
        addRelic(new CBR_Lantern());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnJAX(), extraUpgrades);
                    addToList(cardsList, new EnClothesline(), true);
                    addToList(cardsList, new EnDefendRed(), false);
                    break;
                case 1:
                    addToList(cardsList, new EnFlameBarrier(), false);
                    addToList(cardsList, new EnArmaments(), true);
                    addToList(cardsList, new EnRegret(), false);
                    break;
                case 2:
                    addToList(cardsList, new EnIronWave(), extraUpgrades);
                    addToList(cardsList, new EnBite(), false);
                    addToList(cardsList, new EnBash(), false);
                    break;
                case 3:
                    addToList(cardsList, new EnTwinStrike(), false);
                    addToList(cardsList, new EnIronWave(), true);
                    addToList(cardsList, new EnBite(), false);
                    break;
                case 4:
                    addToList(cardsList, new EnHeavyBlade(), false);
                    addToList(cardsList, new EnDefendRed(), false); // upgraded from armaments
                    addToList(cardsList, new EnBite(), false);
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnInflame(), true); // removed
                    addToList(cardsList, new EnArmaments(), true);
                    addToList(cardsList, new EnDefendRed(), false); // now upgraded
                    break;
                case 1:
                    addToList(cardsList, new EnTwinStrike(), false);
                    addToList(cardsList, new EnIronWave(), extraUpgrades);
                    addToList(cardsList, new EnRegret(), false);
                    break;
                case 2:
                    addToList(cardsList, new EnFlameBarrier(), false);
                    addToList(cardsList, new EnJAX(), extraUpgrades);
                    addToList(cardsList, new EnBite(), false);
                    break;
                case 3:
                    addToList(cardsList, new EnHeavyBlade(), false);
                    addToList(cardsList, new EnDefendRed(), false);
                    addToList(cardsList, new EnIronWave(), true);
                    break;
                case 4:
                    addToList(cardsList, new EnBite(), false);
                    addToList(cardsList, new EnBite(), false);
                    addToList(cardsList, new EnBash(), false);
                    break;
                case 5:
                    addToList(cardsList, new EnSeeingRed(), false);
                    addToList(cardsList, new EnDemonForm(), extraUpgrades); // removed
                    addToList(cardsList, new EnClothesline(), true);
                    break;
            }
        }
        turn++;
        if (turn > 5 && !looped) { looped = true;
            turn = 0;
        }
        else if (turn > 4 && looped) {
            turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Ginger());
    }
}