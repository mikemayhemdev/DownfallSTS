package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.red.*;
import charbosses.powers.bossmechanicpowers.IroncladMushroomPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2MushroomsSimple extends ArchetypeBaseIronclad {

    public ArchetypeAct2MushroomsSimple() {
        super("IC_MUSHROOM_ARCHETYPE", "Mushroom");

        maxHPModifier += 190;
        maxHPModifierAsc = 20;
        actNum = 2;
        bossMechanicName = IroncladMushroomPower.NAME;
        bossMechanicDesc = IroncladMushroomPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnSummonMushrooms());
                    addToList(cardsList, new EnFeedingFrenzy());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnRupture());
                    addToList(cardsList, new EnHemokinesis(), true);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnReaper(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnFlameBarrier(), extraUpgrades);
                    addToList(cardsList, new EnJAX(), extraUpgrades);
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnShockwave());
                    turn = 0;
                    break;
            }

        return cardsList;
    }

}
