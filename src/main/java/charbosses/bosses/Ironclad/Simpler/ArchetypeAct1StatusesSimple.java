package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.*;
import charbosses.powers.bossmechanicpowers.DefectAttackVoidPower;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Barrage;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1StatusesSimple extends ArchetypeBaseIronclad {


    public ArchetypeAct1StatusesSimple() {
        super("IC_STATUS_ARCHETYPE", "Status");

        maxHPModifier += 70;
        maxHPModifierAsc = 10;
        actNum = 1;
    }


    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractCreature m = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new IroncladStatusPower(m)));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        switch (turn) {
            case 0:
                addToList(cardsList, new EnRecklessCharge());
                addToList(cardsList, new EnRecklessCharge());
                turn++;
                break;
            case 1:
                addToList(cardsList, new EnMetallicize(), false);
                addToList(cardsList, new EnSpotWeakness(), false);
                turn++;
                break;
            case 2:
                addToList(cardsList, new EnPowerThrough(true), false);
                addToList(cardsList, new EnWildStrike(), extraUpgrades);
                turn++;
                break;
            case 3:
                addToList(cardsList, new EnBerserk(), extraUpgrades);
                addToList(cardsList, new EnImmolate(), false);
                turn++;
                break;
            case 4:
                addToList(cardsList, new EnIntimidate(), extraUpgrades);
                if (looped) addToList(cardsList, new EnBarricade(), false);
                if (!looped) addToList(cardsList, new EnDemonForm(), extraUpgrades);
                turn=0;
                looped = true;
                break;
        }
        return cardsList;
    }
}