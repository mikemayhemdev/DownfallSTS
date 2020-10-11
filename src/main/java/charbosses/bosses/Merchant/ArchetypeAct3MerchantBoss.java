package charbosses.bosses.Merchant;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.colorless.*;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.purple.*;
import charbosses.cards.red.EnInflame;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_FaceTrader;
import charbosses.relics.EventRelics.CBR_Falling;
import charbosses.relics.EventRelics.CBR_Mausoleum;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ArchetypeAct3MerchantBoss extends ArchetypeBaseMerchant {

    public ArchetypeAct3MerchantBoss() {
        super("DF_ARCHETYPE_MERCHANT", "Merchant");
    }

    public void initialize() {



        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_MercuryHourglass());
        //addRelic(new CBR_Torii());
        //addRelic(new CBR_SelfFormingClay());
        //addRelic(new CBR_IncenseBurner());
        addRelic(new CBR_Calipers());
        //addRelic(new CBR_Girya(3));
        //addRelic(new CBR_Vajra());
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_FossilizedHelix());
        //addRelic(new CBR_BagOfPreparation());
        //addRelic(new CBR_ClockworkSouvenir());
        //addRelic(new CBR_TungstenRod());
        addRelic(new CBR_IceCream());
        //addRelic(new CBR_FusionHammer());

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1
        addToList(cardsList, new EnPanicButton(), extraUpgrades);
        addToList(cardsList, new EnDramaticEntrance(), extraUpgrades);
        addToList(cardsList, new EnTheBomb(), false);

        //Turn 2
        addToList(cardsList, new EnPanacea(), extraUpgrades);
        addToList(cardsList, new EnApotheosis(), true);
        addToList(cardsList, new EnHandOfGreed(), false);

        //Turn 3
        addToList(cardsList, new EnSadisticNature(), false);
        addToList(cardsList, new EnTrip(), false);
        addToList(cardsList, new EnGoodInstincts(), false);

        //Turn 4
        addToList(cardsList, new EnGoodInstincts(), false);
        addToList(cardsList, new EnBlind(), false);
        addToList(cardsList, new EnSwiftStrike(), false);

        //Turn 5
        addToList(cardsList, new EnPanacea(), false);
        addToList(cardsList, new EnSwiftStrike(), false);
        addToList(cardsList, new EnMagnetism(), false);


    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}