package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ModFinisher extends AbstractChampCard {

    public final static String ID = makeID("ModFinisherStrike");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public ModFinisher() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        this.selfRetain = true;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        finisher();
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}