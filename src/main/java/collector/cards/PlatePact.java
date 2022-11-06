package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.isAfflicted;

public class PlatePact extends AbstractCollectorCard {
    public final static String ID = makeID(PlatePact.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public PlatePact() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Order - Torchhead attacks a random enemy. Gain Block equal to unblocked damage dealt.
    }

    public void upp() {
        //
    }
}