package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.CollectorMod;
import collector.actions.DrawCardFromCollectionAction;
import collector.cardmods.PyreMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class FairTrade extends AbstractCollectorCard {
    public final static String ID = makeID(FairTrade.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public FairTrade() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new DrawCardFromCollectionAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}