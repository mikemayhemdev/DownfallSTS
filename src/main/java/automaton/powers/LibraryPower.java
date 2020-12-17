package automaton.powers;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class LibraryPower extends AbstractAutomatonPower {
    public static final String NAME = "Library";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LibraryPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
                if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                    eligibleCardsList.add(c);
                }
            }
            for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
                if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                    eligibleCardsList.add(c);
                }
            }
            for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
                if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                    eligibleCardsList.add(c);
                }
            }
            AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size()-1));
            qCardGet.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(qCardGet, true));
        }
    }
}
