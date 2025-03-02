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
import gremlin.cards.Ward;

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
        int theSize = AbstractDungeon.player.hand.size();
        if (upgraded) {
            AbstractCard s = (new MinorBeam()).makeCopy();
            s.upgrade();
            if (theSize>0) {
                this.addToTop(new MakeTempCardInHandAction(s, theSize-1));
            }
        } else {
            if (theSize>0) {
                this.addToTop(new MakeTempCardInHandAction(new MinorBeam(), theSize - 1));
            }
        }
        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize-1, false));

    }

        public void upp () {
            cardsToPreview.upgrade();
            upgradeDamage(2);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }