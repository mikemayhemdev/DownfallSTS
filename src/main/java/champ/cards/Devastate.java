package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    public Devastate() {
        super(ID, 5, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 3;
        tags.add(ChampMod.FINISHER);
        postInit();
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.hasTag(ChampMod.FINISHER) && c!=this) {
            updateCost(-1);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
        finisher();
    }

    public void upp() {
        upgradeDamage(3);
    }
}