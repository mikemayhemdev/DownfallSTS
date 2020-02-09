package charbosses.bosses.Ironclad;

import charbosses.cards.red.*;
import charbosses.relics.*;

public class ArchetypeIcStrength extends ArchetypeBaseIronclad {

    public ArchetypeIcStrength() {
        super("IC_BLOCK_ARCHETYPE", "Strength");
    }

        public void initialize(){
            super.initialize();
        //Define Signature Cards - These are guaranteed added to the boss's deck based on the Act the boss is fought in
        addCardToList(new EnInflame(),CardBenefitType.SIGNATUREACT1);
        addCardToList(new EnLimitBreak(),CardBenefitType.SIGNATUREACT2);
        addCardToList(new EnDemonForm(),CardBenefitType.SIGNATUREACT3);

        //Define Supporting cards - These are cards useful to the build that are added randomly based on the Act the boss is fought in
        addCardToList(new EnSwordBoomerang(),CardBenefitType.SUPPORTING);
        addCardToList(new EnInflame(),CardBenefitType.SUPPORTING);
        addCardToList(new EnTwinStrike(),CardBenefitType.SUPPORTING);
        addCardToList(new EnSwordBoomerang(),CardBenefitType.SUPPORTING);

        //Define Signature relics - These are guaranteed to be chosen based on the Act the boss is fought in
        addRelicToList(new CBR_RedSkull(), CardBenefitType.SIGNATUREACT1);
        addRelicToList(new CBR_Vajra(), CardBenefitType.SIGNATUREACT2);
        addRelicToList(new CBR_Girya(), CardBenefitType.SIGNATUREACT3);

        //Blacklist Cards & Relics out of the global or class-wide pools
    }


}