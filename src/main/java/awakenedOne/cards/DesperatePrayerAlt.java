package awakenedOne.cards;

import awakenedOne.cards.altDimension.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

import static awakenedOne.AwakenedOneMod.*;

public class DesperatePrayerAlt extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayerAlt.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayerAlt() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(DesperatePrayerAlt.class.getSimpleName() + ".png"));
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for(int i = 0; i < this.magicNumber; ++i) {
            AbstractCard card = null;
            switch(numbers.get(0)) {
                case 0: {
                    card = new Crusher();
                    break;
                }
                case 1: {
                    card = new Daggerstorm();
                    break;
                }
                case 2: {
                    card = new ManaShield();
                    break;
                }
                case 3: {
                    card = new Minniegun();
                    break;
                }
                case 4: {
                    card = new PackRat();
                    break;
                }
                case 5: {
                    card = new Scheme();
                    break;
                }
                case 6: {
                    card = new SignInBlood();
                    break;
                }
                case 7: {
                    card = new SpreadingSpores();
                    break;
                }
                case 8: {
                    card = new TheEncyclopedia();
                    break;
                }
            }

            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
            numbers.remove(0);
        }

        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 2, true, true));
    }


    public void upp() {
        upgradeMagicNumber(2);

    }
}