package charbosses.bosses.Ironclad;

import charbosses.cards.curses.EnDoubt;
import charbosses.cards.red.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Vampires;
import com.megacrit.cardcrawl.relics.LetterOpener;
import com.megacrit.cardcrawl.relics.Shuriken;

public class ArchetypeIcStrike extends ArchetypeBaseIronclad {

    public ArchetypeIcStrike() {
        super("IC_STRIKE_ARCHETYPE", "Strike");
    }

    public void initialize() {
        super.initialize();
        //Define Signature Cards - These are guaranteed added to the boss's deck based on the Act the boss is fought in
        this.addCardToList(new EnImpervious(), CardBenefitType.SIGNATUREACT1);
        this.addCardToList(new EnImpervious(), CardBenefitType.SIGNATUREACT2);
        this.addCardToList(new EnImpervious(), CardBenefitType.SIGNATUREACT3);

        //Define Supporting cards - These are cards useful to the build that are added randomly based on the Act the boss is fought in
        this.addCardToList(new EnWildStrike(), CardBenefitType.SUPPORTING);
        this.addCardToList(new EnTwinStrike(), CardBenefitType.SUPPORTING);
        this.addCardToList(new EnGhostlyArmor(), CardBenefitType.SUPPORTING);
        this.addCardToList(new EnInflame(), CardBenefitType.SUPPORTING);
        this.addCardToList(new EnMetallicize(), CardBenefitType.SUPPORTING);

        //Define Signature relics - These are guaranteed to be chosen based on the Act the boss is fought in
        this.addRelicToList(new CBR_CentennialPuzzle(), CardBenefitType.SIGNATUREACT1);
        this.addRelicToList(new CBR_DollysMirror(), CardBenefitType.SIGNATUREACT2);
        this.addRelicToList(new CBR_Calipers(), CardBenefitType.SIGNATUREACT3);

        //Blacklist Cards & Relics out of the global or class-wide pools
        this.blacklistRelic(CBR_Vampires.ID);
    }


}