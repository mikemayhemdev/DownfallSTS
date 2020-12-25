package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnMiracle;
import charbosses.cards.colorless.EnPanacea;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnParasite;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.purple.*;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1RetainNewAge extends ArchetypeBaseDefect {

    private AbstractBossCard theVeryImportantSandsOfTime = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct1RetainNewAge() {
        super("WA_ARCHETYPE_RETAIN", "Retain");
        bossMechanicName = bossMechanicString.DIALOG[8];
        bossMechanicDesc = bossMechanicString.DIALOG[9];
    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CloakClasp());
        addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_TungstenRod());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    addToList(cardsList, new EnHalt(), false);
                    theVeryImportantPerseverence = new EnPerseverance();
                    theVeryImportantPerseverence.newPrio = 1;
                    addToList(cardsList, theVeryImportantPerseverence, true);
                    theVeryImportantSandsOfTime = new EnSandsOfTime();
                    theVeryImportantSandsOfTime.newPrio = 1;
                    addToList(cardsList, theVeryImportantSandsOfTime, false);
                    addToList(cardsList, new EnClumsy(), false);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnTalkToTheHand(), false);
                    addToList(cardsList, new EnHalt(), false);
                    addToList(cardsList, new EnCrushJoints(), false);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnPanacea(), extraUpgrades);
                    addToList(cardsList, new EnFasting(), false);
                    addToList(cardsList, new EnClumsy(), true);
                    turn++;
                    break;
                case 3:
                    theVeryImportantPerseverence.newPrio = -2;
                    theVeryImportantSandsOfTime.newPrio = -1;
                    addToList(cardsList, new EnHalt(), false);
                    addToList(cardsList, new EnRegret(), false);
                    addToList(cardsList, new EnParasite(), false);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnHalt(), false);
                    addToList(cardsList, new EnCrushJoints(), false);
                    addToList(cardsList, new EnPerseverance(9), true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    addToList(cardsList, new EnRegret(), false);
                    addToList(cardsList, new EnHalt(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnSandsOfTime(3), false);
                    addToList(cardsList, new EnHalt(), false);
                    addToList(cardsList, new EnParasite(), false);
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_MercuryHourglass());
    }
}