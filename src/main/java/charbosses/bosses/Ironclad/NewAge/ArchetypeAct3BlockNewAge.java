package charbosses.bosses.Ironclad.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnShame;
import charbosses.cards.red.*;
import charbosses.cards.status.EnWound;
import charbosses.monsters.Fortification;
import charbosses.powers.bossmechanicpowers.IroncladFortificationPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;

public class ArchetypeAct3BlockNewAge extends ArchetypeBaseIronclad {

    public ArchetypeAct3BlockNewAge() {
        super("IC_BLOCK_ARCHETYPE", "Block");

        maxHPModifier += 250;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IroncladFortificationPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Fortification(), true));

    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());


       // addRelic(new CBR_ThreadAndNeedle());
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_CursedKey());
        addRelic(new CBR_Torii());
        addRelic(new CBR_OddMushroom());
      //  addRelic(new CBR_RedMask());  // gremlin mask
       // addRelic(new CBR_HappyFlower());  // gremlin mask

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
                    addToList(cardsList, new EnFeelNoPain());
                    addToList(cardsList, new EnImpervious());
                    addToList(cardsList, new EnIntimidate());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnFlameBarrier());
                    addToList(cardsList, new EnBodySlam());
                    addToList(cardsList, new EnClumsy());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnImpervious());
                    addToList(cardsList, new EnIntimidate());
                    addToList(cardsList, new EnEntrench());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnMetallicize(), extraUpgrades);
                    addToList(cardsList, new EnBodySlam());
                    addToList(cardsList, new EnDecay());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnPowerThrough());
                    addToList(cardsList, new EnSecondWind());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 5:
                    addToList(cardsList, new EnMetallicize());
                    addToList(cardsList, new EnGhostlyArmor());
                    addToList(cardsList, new EnBodySlam());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnPowerThrough());
                    addToList(cardsList, new EnSecondWind());
                    addToList(cardsList, new EnBodySlam(), true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnFlameBarrier());
                    addToList(cardsList, new EnBodySlam());
                    addToList(cardsList, new EnDecay());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnGhostlyArmor());
                    addToList(cardsList, new EnEntrench());
                    addToList(cardsList, new EnBodySlam());
                    turn++;
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Calipers());
    }
}