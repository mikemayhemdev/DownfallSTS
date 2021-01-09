package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class Rotation extends AbstractSneckoCard {

    public final static String ID = makeID("Rotation");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Rotation() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                ArrayList<AbstractCard> cardsToDiscard = new ArrayList<>();
                for (AbstractCard q : p.hand.group) {
                    if (q.color != AbstractDungeon.player.getCardColor()) {
                        cardsToDiscard.add(q);
                        x++;
                    }
                }
                x += magicNumber;
                att(new DrawCardAction(x));
                for (AbstractCard q : cardsToDiscard) {
                    att(new DiscardSpecificCardAction(q));
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}