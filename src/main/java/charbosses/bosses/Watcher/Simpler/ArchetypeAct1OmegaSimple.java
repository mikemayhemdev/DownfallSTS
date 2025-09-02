package charbosses.bosses.Watcher.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.colorless.EnPanacea;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.CultistRevivePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

import java.util.ArrayList;

public class ArchetypeAct1OmegaSimple extends ArchetypeBaseWatcher {

    public ArchetypeAct1OmegaSimple() {
        super("WA_ARCHETYPE_CALM", "Calm");
        maxHPModifier += 138;
        maxHPModifierAsc = 10;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractMonster cawcaw = new Cultist(-400F, 0);
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(cawcaw, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(cawcaw, cawcaw, new CultistRevivePower(cawcaw, 1), 1, true, AbstractGameAction.AttackEffect.NONE));



    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        AbstractCharBoss b = AbstractCharBoss.boss;
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        switch (turn) {
            case 0:
                addToList(cardsList, new EnAlpha());
                addToList(cardsList, new EnEndIsNigh());
                turn++;
                break;
            case 1:
                addToList(cardsList, new EnWaveOfTheHand());
                addToList(cardsList, new EnDefendPurple());
                turn++;
                break;
            case 2:
                addToList(cardsList, new EnWishPlated(), extraUpgrades);
                addToList(cardsList, new EnPanacea(), false);

                turn++;
                break;
            case 3:
                addToList(cardsList, new EnFalseWorship());
                addToList(cardsList, new EnStrikePurple(), false);
                turn++;
                break;
            case 4:
                addToList(cardsList, new EnTruePeace());
                addToList(cardsList, new EnTruePeace());
                turn++;
                break;
            case 5:
                addToList(cardsList, new EnBeta());
                addToList(cardsList, new EnDefendPurple(), false);
                turn++;
                break;
            case 6:
                addToList(cardsList, new EnWaveOfTheHand());
                addToList(cardsList, new EnDefendPurple());
                turn++;
                break;
            case 7:
                addToList(cardsList, new EnWishPlated(), extraUpgrades);
                addToList(cardsList, new EnPanacea(), false);
                turn++;
                break;
            case 8:
                addToList(cardsList, new EnFalseWorship());
                addToList(cardsList, new EnStrikePurple(), false);
                turn++;
                break;
            case 9:
                addToList(cardsList, new EnTruePeace());
                addToList(cardsList, new EnTruePeace());
                turn++;
                break;
            case 10:
                addToList(cardsList, new EnOmega());
                addToList(cardsList, new EnDefendPurple(), false);
                turn++;
                break;
            case 11:
                addToList(cardsList, new EnTruePeace());
                addToList(cardsList, new EnTruePeace());
                turn++;
                break;
            case 12:
                addToList(cardsList, new EnJudgment());
                addToList(cardsList, new EnTruePeace());
                break;
        }


        return cardsList;
    }


}
