package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnMadness;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitConcentrateAdder;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class ArchetypeAct1SharpshooterNewAge extends ArchetypeBaseIronclad {
    public static final int damageThreshold = 10;

    public ArchetypeAct1SharpshooterNewAge() {
        super("HERMIT_SHARPSHOOTER_ARCHETYPE", "Dead On");

        maxHPModifier += 95;
        actNum = 1;
        bossMechanicName = HermitConcentrateAdder.NAME;
        bossMechanicDesc = HermitConcentrateAdder.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitConcentrationPower(p), damageThreshold));

        AbstractMonster tangerine = new LouseNormal(-400F, 0);
        tangerine.maxHealth += 24;
        tangerine.currentHealth += 24;
        tangerine.powers.add(new StrengthPower(tangerine, 2));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(tangerine, true));
    }


    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Abacus());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_Calipers());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnHeadshot());
                    addToList(cardsList, new EnDefendHermit());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnStrikeHermit());
                    addToList(cardsList, new EnItchyTrigger());
                    addToList(cardsList, new EnItchyTriggerStrikeHermit());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnGhostlyPresence());
                    addToList(cardsList, new EnDive(), extraUpgrades);
                    addToList(cardsList, new EnDefendHermit());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnMadness());
                    addToList(cardsList, new EnRoughhouse());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnDive());
                    addToList(cardsList, new EnDeadeye());
                    addToList(cardsList, new EnStrikeHermit());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnHeadshot());
                    addToList(cardsList, new EnDefendHermit());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnItchyTrigger());
                    addToList(cardsList, new EnItchyTriggerStrikeHermit());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnGhostlyPresence());
                    addToList(cardsList, new EnDive(), extraUpgrades);
                    addToList(cardsList, new EnDefendHermit());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnRoughhouse());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnDive());
                    if (defaultToggle) {
                        addToList(cardsList, new EnInjury());
                    } else
                        addToList(cardsList, new EnDeadeye());
                    addToList(cardsList, new EnStrikeHermit());
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
