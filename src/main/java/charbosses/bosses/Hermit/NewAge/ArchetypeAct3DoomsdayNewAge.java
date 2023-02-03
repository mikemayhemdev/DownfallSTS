package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnBandage;
import charbosses.cards.colorless.EnMasterOfStrategy;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnPain;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitDoomsday;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import java.util.ArrayList;

public class ArchetypeAct3DoomsdayNewAge extends ArchetypeBaseIronclad {

    public ArchetypeAct3DoomsdayNewAge() {
        super("HERMIT_DOOMSDAY_ARCHETYPE", "Doomsday");

        maxHPModifier += 315; //TODO: High ascension bonus HP from periapt
        actNum = 3;
        bossMechanicName = HermitDoomsday.NAME;
        bossMechanicDesc = HermitDoomsday.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitDoomsday(p), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, 1, false), 1));
    }


    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CursedKey());
        addRelic(new CBR_CharredGlove());
        addRelic(new CBR_BrassTacks()); //TODO: En Brass Tacks because Red Scarf will be mega annoying
        addRelic(new CBR_Omamori()); //TODO: Make sure it's 0
    }

    //TODO: Grudge counter that goes up over time? Or check how Malice is working. Oh no, Malice exhausts doubt
    //TODO:

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnShadowCloak());
                    addToList(cardsList, new EnGrudge());
                    addToList(cardsList, new EnGlare());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnPurgatory());
                    addToList(cardsList, new EnMalice());
                    addToList(cardsList, new EnSprayNPray());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnGrudge());
                    addToList(cardsList, new EnSprayNPray());
                    break;
                case 1:
                    addToList(cardsList, new EnMalice());
                    addToList(cardsList, new EnGlare());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnPurgatory());
                    addToList(cardsList, new EnDoubt());
                    addToList(cardsList, new EnInjury());
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_DarkstonePeriapt()); //TODO: En darkstone periapt (no need for functionality)
    }
}