package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Headbutt extends AbstractChampCard {

    public final static String ID = makeID("Headbutt");


    public Headbutt() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
        baseDamage = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction(p));

    }

    public void upp() {
        upgradeDamage(3);
    }
}