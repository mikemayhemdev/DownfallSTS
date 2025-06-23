package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;


public class Slaughter extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Slaughter.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Slaughter() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
        this.cardsToPreview = new Ceremony();
        loadJokeCardImage(this, makeBetaCardPath(Slaughter.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        AbstractCard c = new Ceremony();
        Wiz.atb(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}