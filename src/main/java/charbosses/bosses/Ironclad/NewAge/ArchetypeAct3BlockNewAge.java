package charbosses.bosses.Ironclad.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnShame;
import charbosses.cards.red.*;
import charbosses.cards.status.EnWound;
import charbosses.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;

public class ArchetypeAct3BlockNewAge extends ArchetypeBaseIronclad {

    public ArchetypeAct3BlockNewAge() {
        super("IC_BLOCK_ARCHETYPE", "Block");
        bossMechanicName = bossMechanicString.DIALOG[12];
        bossMechanicDesc = bossMechanicString.DIALOG[13];
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
                    AbstractCard c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;
                    ((EnBodySlam) c).manualCustomDamageModifier += 12;
                    addToList(cardsList, c);
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
                    c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;
                    addToList(cardsList, c);
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
                    addToList(cardsList, new EnGhostlyArmor(), extraUpgrades);
                    c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;
                    if (extraUpgrades) {
                        ((EnBodySlam) c).manualCustomDamageModifier += 10;
                    } else {
                        ((EnBodySlam) c).manualCustomDamageModifier += 13;
                    }
                    addToList(cardsList, c);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnPowerThrough());
                    addToList(cardsList, new EnSecondWind());
                    AbstractCard c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;

                    ((EnBodySlam) c).manualCustomDamageModifier += 25;
                    addToList(cardsList, c, true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnFlameBarrier());
                    addToList(cardsList, new EnBodySlam());
                    c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;

                    ((EnBodySlam) c).manualCustomDamageModifier += 12;
                    addToList(cardsList, c);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnGhostlyArmor(), extraUpgrades);
                    addToList(cardsList, new EnEntrench());
                    c = new EnBodySlam();
                    if (AbstractCharBoss.boss.hasPower(BarricadePower.POWER_ID)) ((EnBodySlam) c).manualCustomDamageModifier += 10;

                    if (extraUpgrades) {
                        ((EnBodySlam) c).manualCustomDamageModifier += 20;
                    } else {
                        ((EnBodySlam) c).manualCustomDamageModifier += 26;
                    }
                    addToList(cardsList, c);
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