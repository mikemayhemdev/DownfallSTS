package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.colorless.EnPanicButton;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnShame;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class ArchetypeAct3OrbsNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;

    public ArchetypeAct3OrbsNewAge() {
        super("DF_ARCHETYPE_ORBS", "Orbs");
        bossMechanicName = bossMechanicString.DIALOG[16];
        bossMechanicDesc = bossMechanicString.DIALOG[17];

        maxHPModifier += 350;
        actNum = 3;
    }

    private void increasePretendFocus(int amount) {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus += amount;
                AbstractEnemyOrb.masterPretendFocus += amount;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }

    public static void resetPretendFocus() {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus = 0;
                AbstractEnemyOrb.masterPretendFocus = 0;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_DataDisk());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_PhilosopherStone());

    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        if (cB == null){
            cB = (CharBossDefect) AbstractCharBoss.boss;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnRainbow());
                    addToList(cardsList, new EnReinforcedBody(), extraUpgrades);
                    addToList(cardsList, new EnChargeBattery());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnZap(), true);
                    addToList(cardsList, new EnDualcast());
                    addToList(cardsList, new EnMulticast(2));
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnBullseye(), true);
                    addToList(cardsList, new EnColdSnap());
                    addToList(cardsList, new EnLeap());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnStorm(), false);
                    addToList(cardsList, new EnDefragment(), true);
                    increasePretendFocus(2);
                    addToList(cardsList, new EnBlind(), extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnLeap());
                    addToList(cardsList, new EnChargeBattery());
                    addToList(cardsList, new EnBullseye(), false);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnBlind(), true);
                    addToList(cardsList, new EnDualcast());
                    addToList(cardsList, new EnMulticast(3));
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnColdSnap(), false);
                    addToList(cardsList, new EnZap(), true);
                    addToList(cardsList, new EnReinforcedBody(), extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_ArtOfWar());
    }
}