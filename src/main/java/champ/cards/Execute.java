package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Execute extends AbstractChampCard {

    public final static String ID = makeID("Execute");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 7;
    //private static final int UPG_DAMAGE = 2;
    private static final int HP_LOSS = 4;

    public Execute() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HP_LOSS;
        baseCool = cool = 2;
        myHpLossCost = 4;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (upgraded) dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        fatigue(magicNumber);
        finisher();
    }

    public void upp() {
        upgradeCool(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}