package charbosses.bosses.Merchant;

import charbosses.relics.*;

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
        /*
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1
        addToDeck(new EnPanicButton(), extraUpgrades);
        addToDeck(new EnDramaticEntrance(), extraUpgrades);
        addToDeck(new EnTheBomb(), false);

        //Turn 2
        addToDeck(new EnPanacea(), extraUpgrades);
        addToDeck(new EnApotheosis(), true);
        addToDeck(new EnHandOfGreed(), false);

        //Turn 3
        addToDeck(new EnSadisticNature(), false);
        addToDeck(new EnTrip(), false);
        addToDeck(new EnGoodInstincts(), false);

        //Turn 4
        addToDeck(new EnGoodInstincts(), false);
        addToDeck(new EnBlind(), false);
        addToDeck(new EnSwiftStrike(), false);

        //Turn 5
        addToDeck(new EnPanacea(), false);
        addToDeck(new EnSwiftStrike(), false);
        addToDeck(new EnMagnetism(), false);
        */


    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}