package awakenedOne.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.DrawSpecificAction;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Clutch extends AbstractAwakenedCard {
    public final static String ID = makeID(Clutch.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Clutch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        //baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Clutch.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new DrawSpecificAction(magicNumber, card -> card.cost == 0));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.drawPile.group.stream().noneMatch(q -> q.cost == 0) ? Color.RED : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
    }
}