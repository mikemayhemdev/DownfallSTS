package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static champ.ChampMod.loadJokeCardImage;

public class Shatter extends AbstractChampCard {

    public final static String ID = makeID("Shatter");

    //stupid intellij stuff skill, self, uncommon

    public Shatter() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 1;
        postInit();
        loadJokeCardImage(this, "Shatter.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // techique();
        atb(new RemoveAllBlockAction(m, p));
        if (upgraded) atb(new RemoveSpecificPowerAction(m, p, ArtifactPower.POWER_ID));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);

    }

    public void upp() {
        upgradeDamage(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}