package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Virus extends AbstractBronzeCard {

    public final static String ID = makeID("Virus");

    private static final int DAMAGE = 4;

    public Virus() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        cardsToPreview = new MinorBeam();
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Virus.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
        int handSize = p.hand.size();
            addToBot(new DiscardAction(p, p, handSize, false));
            AbstractCard tokenCard = new MinorBeam();
            if (upgraded) {
                tokenCard.upgrade();
            }
            if (handSize > 0) {
                addToBot(new MakeTempCardInHandAction(tokenCard, handSize - 1));
            }
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
        upgradeDamage(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
