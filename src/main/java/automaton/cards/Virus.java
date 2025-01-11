package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.FrailPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.LIGHTNING;

public class Virus extends AbstractBronzeCard {

    public final static String ID = makeID("Virus");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 4;

    public Virus() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        cardsToPreview = new MinorBeam();
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Virus.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, LIGHTNING);
        int handSize = p.hand.size();
        if (handSize > 0) {
            addToBot(new DiscardAction(p, p, handSize, false));
        }
        if (handSize > 1) {
            addToBot(new MakeTempCardInHandAction(new MinorBeam(), handSize - 1));

        }
    }

        public void upp () {
            cardsToPreview.upgrade();
            upgradeDamage(2);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }