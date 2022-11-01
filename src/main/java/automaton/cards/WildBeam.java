package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WildBeam extends AbstractBronzeCard {

    public final static String ID = makeID("WildBeam");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    public WildBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> valid = new ArrayList<>();
                valid.addAll(AbstractDungeon.player.drawPile.group.stream().filter(q -> q.type == CardType.STATUS).collect(Collectors.toList()));
                if (!valid.isEmpty()) {
                    att(new ExhaustSpecificCardAction(valid.get(AbstractDungeon.cardRandomRng.random(valid.size() - 1)), AbstractDungeon.player.drawPile));
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}