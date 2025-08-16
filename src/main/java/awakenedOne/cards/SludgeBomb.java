package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;

import static awakenedOne.AwakenedOneMod.*;

public class SludgeBomb extends AbstractAwakenedCard {
    public final static String ID = makeID(SludgeBomb.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public SludgeBomb() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        loadJokeCardImage(this, makeBetaCardPath(SludgeBomb.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        AbstractCard toRemove = null;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof VoidCard || c instanceof IntoTheVoid) {
                toRemove = c;
                break;
            }
        }
        if (toRemove != null) AbstractDungeon.player.exhaustPile.removeCard(toRemove);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.exhaustPile.group.stream().noneMatch(card -> Objects.equals(card.cardID, VoidCard.ID))) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        upgradeDamage(3);
    }
}
