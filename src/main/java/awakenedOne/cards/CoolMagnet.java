package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Collections;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;
import static awakenedOne.util.Wiz.atb;

public class CoolMagnet extends AbstractAwakenedCard {
    public final static String ID = makeID(CoolMagnet.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 2, 1

    public CoolMagnet() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard tar = getCheapestCardInDiscardPile();
                if (tar != null)
                    AbstractDungeon.player.discardPile.moveToHand(tar);
            }
        });
    }

    private static AbstractCard getCheapestCardInDiscardPile() {
        int lowestCost = 99;
        AbstractCard targetCard = null;

        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(AbstractDungeon.player.discardPile.group);
        Collections.shuffle(cards, AbstractDungeon.cardRandomRng.random);

        for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
            int cost = getRealCost(q);
            if (cost != -2)
                if (cost < lowestCost) {
                    lowestCost = cost;
                    targetCard = q;
                }
        }

        return targetCard;
    }

    private static int getRealCost(AbstractCard c) {
        if (c.cost == -2) {
            return 90;
        }
        if (c.cost == -1) {
            return EnergyPanel.totalCount;
        }
        return c.cost;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}