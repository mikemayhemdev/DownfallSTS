package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class Shatter extends AbstractChampCard {

    public final static String ID = makeID("Shatter");

    //stupid intellij stuff skill, self, uncommon

    public Shatter() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        //  tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // techique();
        if (bcombo()) {
            atb(new RemoveAllBlockAction(m, p));
            if (upgraded) atb(new RemoveSpecificPowerAction(m, p, ArtifactPower.POWER_ID));
        }
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}