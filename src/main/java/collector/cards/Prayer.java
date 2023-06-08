package collector.cards;

import collector.actions.AllEnemyLoseHPAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Prayer extends AbstractCollectorCard {
    public final static String ID = makeID(Prayer.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 11, 4, , , 11, 4

    public Prayer() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 11;
        baseMagicNumber = magicNumber = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard q = cards.get(0);
            if (q.originalName.equalsIgnoreCase(cardStrings.NAME)) {
                att(new AllEnemyLoseHPAction(magicNumber));
            }
            att(new DiscardSpecificCardAction(q, AbstractDungeon.player.hand));
        }));
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(4);
    }
}