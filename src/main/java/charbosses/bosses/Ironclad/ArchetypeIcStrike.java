package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.cards.red.*;
import charbosses.relics.CBR_LetterOpener;
import charbosses.relics.CBR_StrikeDummy;
import charbosses.relics.CBR_Vajra;
import charbosses.relics.CBR_Whetstone;
import charbosses.relics.EventRelics.CBR_Vampires;
import com.megacrit.cardcrawl.relics.LetterOpener;

public class ArchetypeIcStrike extends ArchetypeBaseIronclad {

    public ArchetypeIcStrike() {
        super("IC_STRIKE_ARCHETYPE", "Strike");
    }
        public void initialize(){
            super.initialize();
        //Define Signature Cards - These are guaranteed added to the boss's deck based on the Act the boss is fought in
        this.addCardToList(new EnPerfectedStrike(),CardBenefitType.SIGNATUREACT1);
        this.addCardToList(new EnHeadbutt(),CardBenefitType.SIGNATUREACT2);
        this.addCardToList(new EnDoubleTap(),CardBenefitType.SIGNATUREACT3);

        //Define Supporting cards - These are cards useful to the build that are added randomly based on the Act the boss is fought in
        this.addCardToList(new EnWildStrike(),CardBenefitType.SUPPORTING);
        this.addCardToList(new EnTwinStrike(),CardBenefitType.SUPPORTING);

        //Define Signature relics - These are guaranteed to be chosen based on the Act the boss is fought in
        this.addRelicToList(new CBR_Vajra(), CardBenefitType.SIGNATUREACT1);
        this.addRelicToList(new CBR_Whetstone(), CardBenefitType.SIGNATUREACT2);
        this.addRelicToList(new CBR_StrikeDummy(), CardBenefitType.SIGNATUREACT3);

        //Blacklist Cards & Relics out of the global or class-wide pools
        this.blacklistCard(EnSentinel.ID);
        this.blacklistRelic(LetterOpener.ID);
        //this.blacklistRelic(CBR_Vampires.ID);
    }


}