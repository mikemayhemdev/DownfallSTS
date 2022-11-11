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

    private static final int DAMAGE = 6;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public FaceSlap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.COMBO);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        autoVuln(m, 1);
        combo();
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}