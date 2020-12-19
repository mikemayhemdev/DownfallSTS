package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.blue.EnDefendBlue;
import charbosses.cards.purple.*;
import charbosses.powers.WatcherCripplePower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class ArchetypeAct2CalmNewAge extends ArchetypeBaseDefect {

    public ArchetypeAct2CalmNewAge() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
        bossMechanicName = bossMechanicString.DIALOG[4];
        bossMechanicDesc = bossMechanicString.DIALOG[5];
    }

    public void initialize() {
       /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_TeardropLocket());
        addRelic(new CBR_IncenseBurner());
       // addRelic(new CBR_MercuryHourglass());
      //  addRelic(new CBR_ArtOfWar());
        //addRelic(new CBR_Duplicator());
       // addRelic(new CBR_Nest());
        //addRelic(new CBR_PhilosopherStone());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnLikeWater(), false);  //removed
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnRagnarok(), true);  //not used
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnFollowUp(), extraUpgrades);
                    addToList(cardsList, new EnDefendPurple(), false);  //not used
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnLikeWater(), false);
                    addToList(cardsList, new EnDefendPurple(), true);  //not used
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnWallop(), true);
                    addToList(cardsList, new EnBlasphemy(), true);  //not used
                    addToList(cardsList, new EnFlyingSleeves(), true);  //not used
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnWish(), extraUpgrades);  //removed
                    addToList(cardsList, new EnTranquility(), true);  //not used
                    addToList(cardsList, new EnVigilance(), extraUpgrades);
                    turn++;
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 5:
                    addToList(cardsList, new EnEmptyBody(), false);  //not used
                    addToList(cardsList, new EnDevaForm(), false);  //removed
                    addToList(cardsList, new EnConsecrate(), true);
                    turn=0;
                    looped = true;
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    break;
            }

        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnConsecrate(), true);
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnVigilance(), extraUpgrades);
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnFollowUp(), extraUpgrades);
                    addToList(cardsList, new EnDefendPurple(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnFlyingSleeves(), true);
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnWallop(), true);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnRagnarok(), false);
                    addToList(cardsList, new EnDefendPurple(), true);
                    addToList(cardsList, new EnEmptyBody(), false);
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn = 0;
                    break;
            }
        }

        if (AbstractCharBoss.boss.powerhouseTurn){
            AbstractCharBoss.boss.getPower(WatcherCripplePower.POWER_ID).onSpecificTrigger();
        }
        return cardsList;

    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_CloakClasp());
    }

    /*
    AbstractBossCard c = new EnRitualDagger();  //removed
                    c.misc += 5;
                    c.applyPowers();
                    c.baseDamage = c.misc;
                    c.isDamageModified = false;
                    addToDeck(c, true);
     */
}