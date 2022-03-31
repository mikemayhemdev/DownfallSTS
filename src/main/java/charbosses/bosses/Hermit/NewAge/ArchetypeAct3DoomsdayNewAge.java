package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnBandage;
import charbosses.cards.colorless.EnMasterOfStrategy;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnPain;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitDoomsday;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DoomsdayNewAge extends ArchetypeBaseIronclad {

    public ArchetypeAct3DoomsdayNewAge() {
        super("HERMIT_DOOMSDAY_ARCHETYPE", "Doomsday");

        maxHPModifier += 315;
        actNum = 1;
        bossMechanicName = HermitDoomsday.NAME;
        bossMechanicDesc = HermitDoomsday.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitDoomsday(p), 1));
    }


    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CallingBell());
        addRelic(new CBR_Omamori());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_MaskGremlin());
        addRelic(new CBR_CharredGlove());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnCovet(), true);
                    addToList(cardsList, new EnDefendHermit());
                    addToList(cardsList, new EnPain());
                    addToList(cardsList, new EnClumsy());
                    addToList(cardsList, new EnSpite());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnMisfire());
                    addToList(cardsList, new EnGlare());
                    addToList(cardsList, new EnGhostlyPresence());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnMisfire());
                    addToList(cardsList, new EnMasterOfStrategy());
                    addToList(cardsList, new EnLoneWolf());
                    addToList(cardsList, new EnBandage());
                    addToList(cardsList, new EnWideOpen());
                    addToList(cardsList, new EnMalice());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSprayNPray());
                    addToList(cardsList, new EnEnervate());
                    addToList(cardsList, new EnLowProfile());
                    addToList(cardsList, new EnGlare());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnClumsy());
                    addToList(cardsList, new EnClumsy());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 5:
                    addToList(cardsList, new EnPurgatory());
                    addToList(cardsList, new EnGrudge());
                    addToList(cardsList, new EnGrudge());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    AbstractCard q = new EnDoubt();
                    AbstractCard m = new EnMalice();
                    ((EnMalice) m).setExhaust(q);
                    addToList(cardsList, m);
                    addToList(cardsList, q);
                    addToList(cardsList, new EnWideOpen());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnMisfire());
                    addToList(cardsList, new EnSpite());
                    addToList(cardsList, new EnClumsy());
                    addToList(cardsList, new EnPain());
                    addToList(cardsList, new EnGrudge(12));
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnSprayNPray());
                    addToList(cardsList, new EnLowProfile());
                    addToList(cardsList, new EnMisfire());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnCovet(), true);
                    addToList(cardsList, new EnDefendHermit());
                    addToList(cardsList, new EnGlare());
                    addToList(cardsList, new EnGlare());
                    addToList(cardsList, new EnGhostlyPresence());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnPurgatory());
                    addToList(cardsList, new EnEnervate());
                    addToList(cardsList, new EnGrudge(12));
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Orichalcum());
    }
}