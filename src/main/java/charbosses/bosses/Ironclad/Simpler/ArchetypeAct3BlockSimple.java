package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.*;
import charbosses.monsters.Fortification;
import charbosses.powers.bossmechanicpowers.IroncladFortificationPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;

public class ArchetypeAct3BlockSimple extends ArchetypeBaseIronclad {
    public static final int FORTIFICATION_AMOUNT = 10;

    public ArchetypeAct3BlockSimple() {
        super("IC_BLOCK_ARCHETYPE", "Block");

        maxHPModifier += 300;
        maxHPModifierAsc = 30;
        actNum = 3;
        bossMechanicName = IroncladFortificationPower.NAME;
        bossMechanicDesc = IroncladFortificationPower.DESC[0] + FORTIFICATION_AMOUNT + IroncladFortificationPower.DESC[1];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Fortification(), true));

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;



        switch (turn) {
            case 0:
                addToList(cardsList, new EnImpervious(), extraUpgrades);
                addToList(cardsList, new EnIntimidate());
                turn++;
                break;
            case 1:
                addToList(cardsList, new EnIronWave());
                addToList(cardsList, new EnBodySlam());
                turn++;
                break;
            case 2:
                addToList(cardsList, new EnMetallicize(), extraUpgrades);
                addToList(cardsList, new EnMetallicize(), extraUpgrades);
                turn++;
                break;
            case 3:
                addToList(cardsList, new EnGhostlyArmor());
                addToList(cardsList, new EnDisarm(), extraUpgrades);
                turn++;
                break;
            case 4:
                addToList(cardsList, new EnIronWave());
                addToList(cardsList, new EnBodySlam());
                turn = 0;
                looped = true;
                break;
        }


        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}