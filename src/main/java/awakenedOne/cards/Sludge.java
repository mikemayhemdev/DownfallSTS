package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Objects;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Sludge extends AbstractAwakenedCard {
    public final static String ID = makeID(Sludge.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Sludge() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 18;
        loadJokeCardImage(this, makeBetaCardPath(Sludge.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        AbstractCard toRemove = null;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof VoidCard) {
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
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}
