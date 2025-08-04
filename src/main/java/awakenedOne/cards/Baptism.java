package awakenedOne.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.DrawSpecificAction;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public class Baptism extends AbstractAwakenedCard {
    public final static String ID = makeID(Baptism.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Baptism() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Baptism.class.getSimpleName() + ".png"));
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
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}