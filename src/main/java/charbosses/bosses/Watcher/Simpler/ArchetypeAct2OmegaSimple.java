package charbosses.bosses.Watcher.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.purple.*;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

import java.util.ArrayList;

public class ArchetypeAct2OmegaSimple extends ArchetypeBaseWatcher {

    public ArchetypeAct2OmegaSimple() {
        super("WA_ARCHETYPE_CALM", "Calm");
        maxHPModifier += 198;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractMonster cawcaw = new Cultist(-400F, 0);
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(cawcaw, true));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        AbstractCharBoss b = AbstractCharBoss.boss;
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        switch (turn) {
            case 0:
                addToList(cardsList, new EnAlpha());
                addToList(cardsList, new EnEndIsNigh());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 1:
                addToList(cardsList, new EnWishPlated(), false);
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 2:
                addToList(cardsList, new EnTruePeace());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 3:
                addToList(cardsList, new EnTruePeace());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 4:
                addToList(cardsList, new EnBeta());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 5:
                addToList(cardsList, new EnWishPlated(), false);
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 6:
                addToList(cardsList, new EnTruePeace());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 7:
                addToList(cardsList, new EnTruePeace());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 8:
                addToList(cardsList, new EnOmega());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 9:
               // addToList(cardsList, new EnJudgment());
                addToList(cardsList, new EnBlasphemy());
                turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
            case 10:
                addToList(cardsList, new EnTruePeace());
                addToList(cardsList, new EnTruePeace());
                addToList(cardsList, new EnTruePeace());
                turn = 0;
                looped = true;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                break;
        }


        return cardsList;
    }


}
