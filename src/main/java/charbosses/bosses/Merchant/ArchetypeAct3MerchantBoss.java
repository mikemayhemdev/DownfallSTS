package charbosses.bosses.Merchant;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.colorless.*;
import charbosses.cards.curses.EnClumsy;
import charbosses.powers.bossmechanicpowers.MerchantStrengthPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3MerchantBoss extends ArchetypeBaseMerchant {

    public ArchetypeAct3MerchantBoss() {
        super("ME_ARCHETYPE_MERCHANT", "Merchant");
    }

    public void initialize() {


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
      //  addRelic(new CBR_MercuryHourglass());
       // addRelic(new CBR_Torii());
        //addRelic(new CBR_SelfFormingClay());
        //addRelic(new CBR_IncenseBurner());
        //addRelic(new CBR_Calipers());
       // addRelic(new CBR_Girya(3));
       // addRelic(new CBR_Vajra());
      //  addRelic(new CBR_SmoothStone());

        // :)
        addRelic(new CBR_SmilingMask());

        //addRelic(new CBR_BagOfPreparation());

        addRelic(new CBR_Toolbox());

        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_Torii());
    }


    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MerchantStrengthPower(p)));
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnPanacea()); //Toolbox
                    addToList(cardsList, new EnPanicButton()); //exhausts
                    addToList(cardsList, new EnDramaticEntrance()); //exhausts
                    addToList(cardsList, new EnTheBomb(), extraUpgrades); //1 of 1 Bombs
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnApotheosis(), true); //exhausts
                    addToList(cardsList, new EnPanacea(), true); // +2 artifact
                    addToList(cardsList, new EnGoodInstincts(), true); //1 of 2 Good Instincts
                    turn++;
                    break;
                case 2:
                    //Bomb goes Boom.
                    addToList(cardsList, new EnPanacea(), true); // +2 artifact, +5 total??????? remove clockwork and replace with another relic (abacus?)
                    addToList(cardsList, new EnPanicButton(), true);
                    addToList(cardsList, new EnClumsy());
                    //addToList(cardsList, new EnSadisticNature(), true); // sadistic here fills up a slot because there was an extra unaccounted for bomb
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSwiftStrike(), true); //1 of 2 Swift Strikes
                    addToList(cardsList, new EnTrip(), true); // moved the trip here
                    addToList(cardsList, new EnBlind(), true); //moved a blind here
                    // 1 of 1 Trips
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnGoodInstincts(), true); //2 of 2 Good Instincts
                    addToList(cardsList, new EnSwiftStrike(), true); //2 of 2 Swift Strikes
                    addToList(cardsList, new EnHandOfGreed(), true); //1 of 1 Hand of Greed
                    turn=0;
                    looped = true;
                    break;
            }

        } else {
            switch (turn) {
                case 0:
                    //no bomb this turn
                    addToList(cardsList, new EnTheBomb(), true); //1
                    addToList(cardsList, new EnSwiftStrike(), true); //2
                    addToList(cardsList, new EnTrip(), true); //3
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnHandOfGreed(), true); //4
                    addToList(cardsList, new EnTheBomb(), true); //5
                    addToList(cardsList, new EnBlind(), true); //6
                    turn++;
                    break;
                case 2:
                    //Bomb goes Boom.
                    addToList(cardsList, new EnGoodInstincts(), true); //7
                    addToList(cardsList, new EnSwiftStrike(), true); //8
                    addToList(cardsList, new EnGoodInstincts(), true); //9
                    //changing this turn because this is too harsh compared to normal act 3 bosses, and this number could be higher depending on # of shops visited
                    turn = 0;
                    break;
            }
// 1 Bomb. 2 Swift Strikes. 2 Good Instincts. 1 Trip. 1 Blind. 1 Hand of Greed. that's 9 cards total for a 3 turn loop
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}