package collector.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.att;

public class GreaterHurting extends AbstractCollectorCard {
    public final static String ID = makeID(GreaterHurting.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 2, , , 14, 2

    public GreaterHurting() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 20;
        selfRetain = true;
        cardsToPreview = new GreatestHurting();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onRetained() {
        AbstractCard prev = this;
        AbstractCard replacement = new GreatestHurting();
        if (upgraded) {
            replacement.upgrade();
        }
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
        upgradeDamage(8);
        uDesc();
        cardsToPreview.upgrade();
    }
}