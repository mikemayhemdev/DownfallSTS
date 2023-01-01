package awakenedOne.cards;

import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class OneForOne extends AbstractAwakenedCard {
    public final static String ID = makeID(OneForOne.class.getSimpleName());
    // intellij stuff attack, enemy, common, 6, 1, , , 1, 1

    public OneForOne() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        for (int i = 0; i < magicNumber; i++)
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    ArrayList<AbstractCard> possibleCards = new ArrayList<>();
                    AbstractDungeon.player.drawPile.group.stream().filter(q -> q.cost == 0).forEach(q -> possibleCards.add(q));
                    if (!possibleCards.isEmpty()) {
                        AbstractCard tar = Wiz.getRandomItem(possibleCards, AbstractDungeon.cardRandomRng);
                        AbstractDungeon.player.drawPile.moveToHand(tar);
                    }
                }
            });
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}