package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.patches.HistoryRepeatsPatch;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class HistoryRepeats extends AbstractTimeEaterCard {
    public final static String ID = makeID("HistoryRepeats");
    // intellij stuff skill, self, uncommon, , , , , , 

    public HistoryRepeats() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (HistoryRepeatsPatch.cardsList != null) {
                    AbstractDungeon.player.hand.clear();
                    for (AbstractCard q : HistoryRepeatsPatch.cardsList) {
                        AbstractDungeon.player.hand.addToBottom(q);
                    }
                }
            }
        });
    }

    public void upp() {
        selfRetain = true;
        uDesc();
    }
}