package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.shuffleIn;

public class SparkStrike extends AbstractCollectorCard {
    public final static String ID = makeID(SparkStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 4, , , , 

    public SparkStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        cardsToPreview = new Spark();
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        AbstractCard q = new Spark();
        if (upgraded) q.upgrade();
        shuffleIn(q);
    }

    public void upp() {
        upgradeDamage(1);
        AbstractCard q = new Spark();
        q.upgrade();
        cardsToPreview = q;
    }
}