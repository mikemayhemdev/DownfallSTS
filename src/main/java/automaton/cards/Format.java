package automaton.cards;

import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Format extends AbstractBronzeCard {

    public final static String ID = makeID("Format");

    //stupid intellij stuff attack, all, rare

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public Format() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            att(new GainEnergyAction(1));
            att(new DrawCardAction(effect + params[0]));
            for (int i = 0; i < effect; i++) {
                addToTop(new GainBlockAction(p, block));
            }
            for (int i = 0; i < effect; i++) {
                addToTop(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}