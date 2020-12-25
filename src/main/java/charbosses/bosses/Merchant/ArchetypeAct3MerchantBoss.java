package charbosses.bosses.Merchant;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.colorless.*;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.green.*;
import charbosses.cards.purple.*;
import charbosses.cards.red.EnInflame;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_FaceTrader;
import charbosses.relics.EventRelics.CBR_Falling;
import charbosses.relics.EventRelics.CBR_Mausoleum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import charbosses.relics.*;

import java.util.ArrayList;

public class ArchetypeAct3MerchantBoss extends ArchetypeBaseMerchant {

    public ArchetypeAct3MerchantBoss() {
        super("DF_ARCHETYPE_MERCHANT", "Merchant");
    }

    public void initialize() {


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_MercuryHourglass());
        addRelic(new CBR_Torii());
        //addRelic(new CBR_SelfFormingClay());
        //addRelic(new CBR_IncenseBurner());
        addRelic(new CBR_Calipers());
        addRelic(new CBR_Girya(3));
        addRelic(new CBR_Vajra());
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_FossilizedHelix());
        //addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_ClockworkSouvenir());
        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_IceCream());
        //addRelic(new CBR_FusionHammer());


    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnTheBomb(), true);
                    addToList(cardsList, new EnTrip(), true);
                    addToList(cardsList, new EnSwiftStrike(), true);
                    break;
                case 1:
                    addToList(cardsList, new EnHandOfGreed(), true);
                    addToList(cardsList, new EnGoodInstincts(), true);
                    addToList(cardsList, new EnBlind(), true);
                    break;
                case 2:
                    addToList(cardsList, new EnTheBomb(), true);
                    addToList(cardsList, new EnGoodInstincts(), true);
                    addToList(cardsList, new EnSwiftStrike(), true);
                    break;
            }
        } else {
            switch (turn) {
                case 0:

                    addToList(cardsList, new EnPanicButton(), extraUpgrades);  //removed
                    addToList(cardsList, new EnDramaticEntrance(), extraUpgrades);  //removed
                    addToList(cardsList, new EnTheBomb(), false);
                    break;
                case 1:
                    addToList(cardsList, new EnPanacea(), true);  //removed
                    addToList(cardsList, new EnApotheosis(), true);  //removed
                    addToList(cardsList, new EnHandOfGreed(), false);
                    break;
                case 2:
                    addToList(cardsList, new EnSadisticNature(), true);  //removed
                    addToList(cardsList, new EnTrip(), true);
                    addToList(cardsList, new EnGoodInstincts(), true);
                    break;
                case 3:
                    addToList(cardsList, new EnGoodInstincts(), true);
                    addToList(cardsList, new EnBlind(), true);
                    addToList(cardsList, new EnSwiftStrike(), true);
                    break;
                case 4:
                    addToList(cardsList, new EnPanacea(), true);  //removed
                    addToList(cardsList, new EnSwiftStrike(), true);
                    addToList(cardsList, new EnTheBomb(), true);
                    break;
            }
        }
        turn++;
        if (turn > 4 && !looped)  {looped = true; turn=0;}
        else if (turn > 2 && looped) {
            turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}