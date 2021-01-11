package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class FaceSlap extends AbstractChampCard {

    public final static String ID = makeID("FaceSlap");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public FaceSlap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (m.hasPower(VulnerablePower.POWER_ID)) {
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.BLUNT_HEAVY));
                }
            }
        });
        if (bcombo()) applyToEnemy(m, autoVuln(m, magicNumber));
        berserkOpen();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}