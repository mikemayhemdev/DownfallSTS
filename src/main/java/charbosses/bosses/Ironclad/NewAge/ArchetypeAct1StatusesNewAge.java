package charbosses.bosses.Ironclad.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnDramaticEntrance;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnIcky;
import charbosses.cards.curses.EnMalfunctioning;
import charbosses.cards.red.*;
import charbosses.cards.status.EnBurn;
import charbosses.cards.status.EnDazed;
import charbosses.cards.status.EnWound;
import charbosses.powers.bossmechanicpowers.DefectVoidPower;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class ArchetypeAct1StatusesNewAge extends ArchetypeBaseIronclad {

    boolean secondLoop = false;

    private AbstractCharbossRelic theArtOfWar;

    public ArchetypeAct1StatusesNewAge() {
        super("IC_STATUS_ARCHETYPE", "Status");

        maxHPModifier += 60;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IroncladStatusPower(p)));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        theArtOfWar = new CBR_ArtOfWar();
        addRelic(theArtOfWar);
        //addRelic(new CBR_FossilizedHelix());
        //addRelic(new CBR_Orichalcum());
        addRelic(new CBR_ChampionsBelt());

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBash());
                    addToList(cardsList, new EnMalfunctioning());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnRecklessCharge());
                    addToList(cardsList, new EnWildStrike(), extraUpgrades);
                    addToList(cardsList, new EnDefendRed());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnPowerThrough(true));
                    addToList(cardsList, new EnSecondWind());
                    addToList(cardsList, new EnDefendRed());
                    theArtOfWar.beginPulse();
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSeeingRed());
                    addToList(cardsList, new EnImmolate());
                    addToList(cardsList, new EnIcky());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnTrueGrit(), true);
                    addToList(cardsList, new EnTrueGrit(), true);
                    addToList(cardsList, new EnWound());
                    theArtOfWar.beginPulse();
                    turn++;
                    break;
                case 5:
                    addToList(cardsList, new EnThunderclap(), true);
                    addToList(cardsList, new EnFeelNoPain());
                    addToList(cardsList, new EnDazed());
                    turn=0;
                    looped=true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnPowerThrough(true));
                    addToList(cardsList, new EnTrueGrit(), true);
                    //IC will exhaust the Doubt the first time through the loop instead of a wound
                    //So the wound will appear the second time through the loop onward
                    addToList(cardsList, new EnWound());
                    turn++;
                    theArtOfWar.beginPulse();
                    break;
                case 1:
                    addToList(cardsList, new EnWildStrike(), extraUpgrades);
                    addToList(cardsList, new EnRecklessCharge());
                    addToList(cardsList, new EnBash());  //unused
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnSecondWind());
                    addToList(cardsList, new EnBurn());
                    addToList(cardsList, new EnWound());
                    theArtOfWar.beginPulse();
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnImmolate());
                    addToList(cardsList, new EnThunderclap(), true);
                    addToList(cardsList, new EnDazed());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnTrueGrit(), true);
                    addToList(cardsList, new EnDefendRed());
                    addToList(cardsList, new EnWound());
                    theArtOfWar.beginPulse();
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Orichalcum());
    }
}