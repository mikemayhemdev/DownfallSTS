package champ.cards;

import champ.ChampMod;
import champ.actions.DevastateAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 24;

    public Devastate() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);


        this.misc = DAMAGE;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = this.misc;
        tags.add(ChampMod.FINISHER);
        exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DevastateAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber, this.uuid));
        finisher();
    }

    public void applyPowers() {
        baseDamage = misc;
        super.applyPowers();
        //genPreview();
        initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}