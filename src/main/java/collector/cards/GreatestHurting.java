package collector.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.att;

public class GreatestHurting extends AbstractCollectorCard {
    public final static String ID = makeID(GreatestHurting.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 2, , , 14, 2

    public GreatestHurting() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 30;
        selfRetain = true;
        cardsToPreview = new Ember();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onRetained() {
        AbstractCard prev = this;
        AbstractCard replacement = new Ember();
        att(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int idx = AbstractDungeon.player.hand.group.indexOf(prev);
                AbstractDungeon.player.hand.removeCard(prev);
                AbstractDungeon.player.hand.group.add(idx, replacement);
                replacement.superFlash(Color.PURPLE.cpy());
            }
        });
    }

    public void upp() {
        upgradeDamage(12);
    }
}