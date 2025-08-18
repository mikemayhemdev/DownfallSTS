package charbosses.bosses.Watcher.Simpler;

import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.purple.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DivinitySimple extends ArchetypeBaseWatcher {


    public ArchetypeAct3DivinitySimple() {
        super("WA_ARCHETYPE_DIVINITY", "Divinity");

        maxHPModifier += 348;
        actNum = 3;
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
            switch (turn) {
                case 0:
                    //turn 1
                    addToList(cardsList, new EnFleetingFaith());
                    addToList(cardsList, new EnWish(), true);
                    turn++;
                    break;
                case 1:
                    //turn 2
                    addToList(cardsList, new EnFlyingSleeves());
                    addToList(cardsList, new EnProstrate(), extraUpgrades);  // This is probably a terrible idea. This is where the Blind was, which was moved to where the Strike was.
                    turn++;
                    break;
                case 2:
                    //turn 3
                    addToList(cardsList, new EnWorship());
                    turn++;
                    break;
                case 3:
                    //turn 4
                    addToList(cardsList, new EnRagnarok());
                    turn++;
                    break;
                case 4:
                    //turn 5
                    addToList(cardsList, new EnDevotion());
                    turn++;
                    break;
                case 5:
                    //turn 6
                    addToList(cardsList, new EnBrilliance());
                    turn = 1;
                    looped = true;
                    break;


        }

        return cardsList;
    }

}