package awakenedOne.cards;

import awakenedOne.powers.EntanglePowersPower;
import awakenedOne.relics.KTRibbon;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import downfall.powers.NextTurnPowerPower;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.AwakenButton.awaken;

public class SacrilegiousStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(SacrilegiousStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SacrilegiousStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseBlock = 12;
        this.exhaust = false;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.atb(new ApplyPowerAction(p, p, new NextTurnPowerPower(p, new EntanglePowersPower(1)), 1));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}