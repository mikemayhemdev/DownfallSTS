package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CutThrough extends AbstractBronzeCard {

    public final static String ID = makeID("CutThrough");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public CutThrough() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseAuto = auto = 1;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new ScryAction(magicNumber));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            atb(new DrawCardAction(auto));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}