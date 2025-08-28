package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentDelayedWraithPower;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3ShivsSimple extends ArchetypeBaseSilent {

    public ArchetypeAct3ShivsSimple() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 350;
        maxHPModifierAsc = 30;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentShivTimeEaterPower(AbstractCharBoss.boss)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentDelayedWraithPower(AbstractCharBoss.boss)));

    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnAccuracy(), extraUpgrades);
                    addToList(cardsList, new EnCloakAndDagger(), true);
                    turn++;
                    break;
                case 1:
                    //Turn 3
                    addToList(cardsList, new EnPiercingWail(),extraUpgrades);
                    addToList(cardsList, new EnPiercingWail());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBladeDance(),true);
                    addToList(cardsList, new EnPhantasmalKiller());
                    turn++;
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnFinisher(2),extraUpgrades);
                    turn=0;
                    break;

            }

        return cardsList;
    }

}
