package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.green.*;
import charbosses.cards.purple.EnFlyingSleeves;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2MirrorImageNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct2MirrorImageNewAge() {
        super("SI_MIRROR_ARCHETYPE", "Mirror");
        bossMechanicName = bossMechanicString.DIALOG[10];
        bossMechanicDesc = bossMechanicString.DIALOG[11];

        maxHPModifier += 220;
        actNum = 2;
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        // addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_Ginger());
        addRelic(new CBR_Anchor());
        addRelic(new CBR_PaperKrane());
        // addRelic(new CBR_DreamCatcher());
        // addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
        // addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
        // addRelic(new CBR_WeMeetAgain());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnAfterImage(), true);
                    addToList(cardsList, new EnBackstab());
                    addToList(cardsList, new EnDoppleganger(), true);  //Removed
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnNightmare(), false);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnJAX(), true);
                    addToList(cardsList, new EnDaggerSpray());
                    addToList(cardsList, new EnDecay());  //Not played here
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnOutmaneuver());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnBlur());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnFootwork()); //Removed
                    addToList(cardsList, new EnLegSweep()); //Removed
                    addToList(cardsList, new EnRiddleWithHoles(), extraUpgrades);  //Not played here
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnJAX(), true);
                    addToList(cardsList, new EnDeflect());
                    addToList(cardsList, new EnDecay());  //Not played here
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDaggerSpray());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnOutmaneuver());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnLegSweep()); //Removed
                    addToList(cardsList, new EnRiddleWithHoles(), extraUpgrades);  //Not played here
                    addToList(cardsList, new EnBlur());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_PenNib());
    }
}