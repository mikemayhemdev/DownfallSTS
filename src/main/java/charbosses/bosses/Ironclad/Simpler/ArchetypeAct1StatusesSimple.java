package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.*;
import charbosses.powers.bossmechanicpowers.DefectAttackVoidPower;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import charbosses.powers.cardpowers.EnemyBerserkPower;
import charbosses.relics.CBR_ChampionsBelt;
import charbosses.relics.CBR_Ginger;
import charbosses.relics.CBR_MarkOfPain;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Barrage;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.util.Wiz;

import java.util.ArrayList;

public class ArchetypeAct1StatusesSimple extends ArchetypeBaseIronclad {


    public ArchetypeAct1StatusesSimple() {
        super("IC_STATUS_ARCHETYPE", "Status");

        maxHPModifier += 90;
        maxHPModifierAsc = 10;

    }


    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractCreature m = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new IroncladStatusPower(m)));

        addRelic(new CBR_Ginger());
        if (AbstractDungeon.ascensionLevel >= 19){
            addRelic(new CBR_MarkOfPain());
        }
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
                addToList(cardsList, new EnSpotWeakness(), extraUpgrades);
                turn++;
                break;
            case 2:
                addToList(cardsList, new EnPowerThrough(true), false);
                int x = 1;
                if (AbstractCharBoss.boss.hasPower(EnemyBerserkPower.POWER_ID)){
                    x = x + AbstractCharBoss.boss.getPower(EnemyBerserkPower.POWER_ID).amount;
                }
                addToList(cardsList, new EnWhirlwind(x), false);
                turn++;
                break;
            case 3:
                addToList(cardsList, new EnBerserk(), false);
                addToList(cardsList, new EnImmolate(), false);
                turn++;
                break;
            case 4:
                addToList(cardsList, new EnIntimidate(), true);
                if (looped) addToList(cardsList, new EnBarricade(), false);
                if (!looped) addToList(cardsList, new EnDemonForm(), false);
                turn=0;
                looped = true;
                break;
        }
        return cardsList;
    }
}