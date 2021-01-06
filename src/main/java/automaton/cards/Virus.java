package automaton.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;

public class Virus extends AbstractBronzeCard {

    public final static String ID = makeID("Virus");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 5;

    public Virus() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        baseMagicNumber = magicNumber = 2;
    }


    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            shuffleIn(makeStatEquivalentCopy(), magicNumber);
        }
    }

    @Override
    public String getSpecialCompileText() {
        return EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void upgrade() {
        upgradeDamage(3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, FIRE);
    }

    public void upp() {
    }
}