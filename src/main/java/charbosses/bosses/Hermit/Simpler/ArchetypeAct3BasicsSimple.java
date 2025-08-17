package charbosses.bosses.Hermit.Simpler;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.hermit.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.monsters.DoomedDagger;

import java.util.ArrayList;
import java.util.Collections;

public class ArchetypeAct3BasicsSimple extends ArchetypeBaseIronclad {
    public    ArrayList<AbstractCard> turn23order = new ArrayList<>();
    public ArchetypeAct3BasicsSimple() {
        super("HERMIT_DOOMSDAY_ARCHETYPE", "Doomsday");

        maxHPModifier += 368;
        actNum = 3;
    }

    public static AbstractMonster getDoomedSnake(){ // called by EnPurgatory too
        return new DoomedDagger(-400, 200);
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


            switch (turn) {
                case 0:
                    addToList(cardsList, new EnArsenal());
                    addToList(cardsList, new EnMaintenance());
                    turn23order.clear();
                    turn23order.add(new EnStrikeHermit());
                    turn23order.add(new EnStrikeHermit());
                    turn23order.add(new EnStrikeHermit());
                    turn23order.add(new EnDefendHermit());
                    turn23order.add(new EnDefendHermit());
                    turn23order.add(new EnDefendHermit());
                    Collections.shuffle(turn23order, AbstractDungeon.cardRandomRng.random);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, turn23order.get(0), false);
                    addToList(cardsList, turn23order.get(1), true);
                    addToList(cardsList, turn23order.get(2), extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, turn23order.get(3), extraUpgrades);
                    addToList(cardsList, turn23order.get(4), extraUpgrades);
                    addToList(cardsList, turn23order.get(5), true);
                    turn=0;
                    looped = true;
                    break;

        }
        return cardsList;
    }

}