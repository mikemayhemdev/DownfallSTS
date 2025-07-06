package awakenedOne.cards;

import awakenedOne.powers.EntanglePowersPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class SacrilegiousStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(SacrilegiousStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SacrilegiousStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 5;
        baseBlock = 5;
        this.exhaust = false;
        tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, makeBetaCardPath(SacrilegiousStrike.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToSelf(new EntanglePowersPower(1));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}