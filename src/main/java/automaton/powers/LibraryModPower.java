package automaton.powers;

import automaton.cards.SpaghettiCode;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.cardmods.EtherealMod;

public class LibraryModPower extends AbstractAutomatonPower {
    public static final String NAME = "LibraryMod";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LibraryModPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < amount; i++) {
                AbstractCard qCardGet = SpaghettiCode.getRandomEncode();
                //qCardGet.modifyCostForCombat(-99);
                CardModifierManager.addModifier(qCardGet, new EtherealMod());
                addToBot(new MakeTempCardInHandAction(qCardGet, true));
            }
        }
    }
}
