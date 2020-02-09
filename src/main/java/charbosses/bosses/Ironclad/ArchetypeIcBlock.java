package charbosses.bosses.Ironclad;

import charbosses.cards.red.*;
import charbosses.relics.CBR_CaptainsWheel;
import charbosses.relics.CBR_SelfFormingClay;
import charbosses.relics.CBR_Shuriken;
import charbosses.relics.CBR_SmoothStone;

public class ArchetypeIcBlock extends ArchetypeBaseIronclad {

    public ArchetypeIcBlock() {
        super("IC_BLOCK_ARCHETYPE", "Block");

    }

    public void initialize(){
        super.initialize();
        //Define Signature Cards - These are guaranteed added to the boss's deck based on the Act the boss is fought in
        addCardToList(new EnMetallicize(),CardBenefitType.SIGNATUREACT1);
        addCardToList(new EnJuggernaut(),CardBenefitType.SIGNATUREACT2);
        addCardToList(new EnBarricade(),CardBenefitType.SIGNATUREACT3);

        //Define Supporting cards - These are cards useful to the build that are added randomly based on the Act the boss is fought in
        addCardToList(new EnBodySlam(),CardBenefitType.SUPPORTING);
        addCardToList(new EnIronWave(),CardBenefitType.SUPPORTING);
        addCardToList(new EnTrueGrit(),CardBenefitType.SUPPORTING);
        addCardToList(new EnGhostlyArmor(),CardBenefitType.SUPPORTING);
        addCardToList(new EnImpervious(),CardBenefitType.SUPPORTING);
        addCardToList(new EnEntrench(),CardBenefitType.SUPPORTING);
        addCardToList(new EnMetallicize(),CardBenefitType.SUPPORTING);

        //Define Signature relics - These are guaranteed to be chosen based on the Act the boss is fought in
        addRelicToList(new CBR_SelfFormingClay(), CardBenefitType.SIGNATUREACT1);
        addRelicToList(new CBR_CaptainsWheel(), CardBenefitType.SIGNATUREACT2);
        addRelicToList(new CBR_SmoothStone(), CardBenefitType.SIGNATUREACT3);

        //Blacklist Cards & Relics out of the global or class-wide pools

    }


}