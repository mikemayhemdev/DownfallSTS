package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Repair extends AbstractBronzeCard {

    public final static String ID = makeID("Repair");

    //stupid intellij stuff skill, self, uncommon

    public Repair() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(AbstractDungeon.player.exhaustPile.group, 1, EXTENDED_DESCRIPTION[0], false, c -> c instanceof FunctionCard, (cards) -> {
            cards.forEach(c -> AbstractDungeon.player.exhaustPile.moveToHand(c));
        }));
    }


    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}