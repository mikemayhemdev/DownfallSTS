package charbosses.bosses.Ironclad.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnHaunted;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.red.*;
import charbosses.powers.bossmechanicpowers.DefectVoidPower;
import charbosses.powers.bossmechanicpowers.IroncladMushroomPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;

public class ArchetypeAct2MushroomsNewAge extends ArchetypeBaseIronclad {

    public ArchetypeAct2MushroomsNewAge() {
        super("IC_MUSHROOM_ARCHETYPE", "Mushroom");

        maxHPModifier += 160;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IroncladMushroomPower(p)));
    }


    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());


       // addRelic(new CBR_ThreadAndNeedle());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_MagicFlower());
        addRelic(new CBR_Vajra());
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
                    addToList(cardsList, new EnTwinStrike());
                    addToList(cardsList, new EnFeed());
                    addToList(cardsList, new EnClothesline());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnSummonMushrooms());
                    addToList(cardsList, new EnGhostlyArmor(), true);
                    addToList(cardsList, new EnHemokinesis());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnReaper());
                    addToList(cardsList, new EnDefendRed());
                    addToList(cardsList, new EnHaunted());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSwiftStrike());
                    addToList(cardsList, new EnHeavyBlade());
                    addToList(cardsList, new EnClumsy());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnBloodletting());
                    addToList(cardsList, new EnHeadbutt());
                    addToList(cardsList, new EnFlameBarrier(), true);
                    turn++;
                    break;
                case 5:
                    addToList(cardsList, new EnSummonMushrooms());
                    addToList(cardsList, new EnInflame(), extraUpgrades);
                    addToList(cardsList, new EnStrikeRed());
                    turn=0;
                    looped=true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBloodletting());
                    addToList(cardsList, new EnHeavyBlade());
                    addToList(cardsList, new EnReaper());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnTwinStrike());
                    addToList(cardsList, new EnGhostlyArmor(), true);
                    addToList(cardsList, new EnSwiftStrike());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnClothesline());
                    addToList(cardsList, new EnStrikeRed());
                    addToList(cardsList, new EnFlameBarrier());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSummonMushrooms());
                    addToList(cardsList, new EnHemokinesis());
                    addToList(cardsList, new EnHeadbutt());
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_DuvuDoll(2));
    }
}