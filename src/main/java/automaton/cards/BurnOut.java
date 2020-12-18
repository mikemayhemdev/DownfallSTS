package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurnOut extends AbstractBronzeCard {

    public final static String ID = makeID("BurnOut");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 4;

    public BurnOut() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        thisEncodes();
        AbstractCard q = new Burn();
        q.upgrade();
        cardsToPreview = q;
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        if (forGameplay) {
            AbstractCard q = new Burn();
            q.upgrade();
            if (upgraded) {
                atb(new MakeTempCardInDiscardAction(q, 1));
            }
            else {
                shuffleIn(q, 1);
            }
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}