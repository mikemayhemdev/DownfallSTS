package automaton.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.LIGHTNING;

public class Virus extends AbstractBronzeCard {

    public final static String ID = makeID("Virus");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 3;

    public Virus() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        cardsToPreview = new MinorBeam();
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, LIGHTNING);
        for (AbstractCard c:AbstractDungeon.player.hand.group){
            if (c != this) {
                atb(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                AbstractCard c2 = new MinorBeam();
                if (upgraded) c2.upgrade();
                atb(new MakeTempCardInHandAction(c2));
            }
        }
    }

    public void upp() {
        cardsToPreview.upgrade();
        upgradeDamage(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}