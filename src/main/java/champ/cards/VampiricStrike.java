package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VampiricStrike extends AbstractChampCard {

    public final static String ID = makeID("VampiricStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;

    public VampiricStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (upgraded || bcombo()) {
            atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (!upgraded && bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}