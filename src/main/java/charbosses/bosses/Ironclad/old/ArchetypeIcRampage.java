package charbosses.bosses.Ironclad.old;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.EnDoubleTap;
import charbosses.cards.red.EnHeadbutt;
import charbosses.cards.red.EnRampage;
import charbosses.cards.red.EnTrueGrit;
import charbosses.relics.CBR_Shuriken;

public class ArchetypeIcRampage extends ArchetypeBaseIronclad {

    public ArchetypeIcRampage() {
        super("IC_RAMPAGE_ARCHETYPE", "Rampage");
    }

    public void initialize() {
        super.initialize();
        //Define Signature Cards - These are guaranteed added to the boss's deck based on the Act the boss is fought in
        addCardToList(new EnRampage(), CardBenefitType.SIGNATUREACT1);
        addCardToList(new EnHeadbutt(), CardBenefitType.SIGNATUREACT2);
        addCardToList(new EnDoubleTap(), CardBenefitType.SIGNATUREACT3);

        //Define Supporting cards - These are cards useful to the build that are added randomly based on the Act the boss is fought in
        addCardToList(new EnDoubleTap(), CardBenefitType.SUPPORTING);
        addCardToList(new EnHeadbutt(), CardBenefitType.SUPPORTING);
        addCardToList(new EnTrueGrit(), CardBenefitType.SUPPORTING);

        //Define Signature relics - These are guaranteed to be chosen based on the Act the boss is fought in
        addRelicToList(new CBR_Shuriken(), CardBenefitType.SIGNATUREACT3);

        //Blacklist Cards & Relics out of the global or class-wide pools
    }


}