package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VampiricStrike extends AbstractChampCard {

    public final static String ID = makeID("VampiricStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;

    public VampiricStrike() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.TECHNIQUE);
        magicNumber = baseMagicNumber = 2;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();

        atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (magicNumber - 2 > 0) {
            for (int i = 0; i < magicNumber - 2; i++) {
                if (upgraded)
                    atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }

    }


    public void upp() {
        upgradeBaseCost(2);
    }
}