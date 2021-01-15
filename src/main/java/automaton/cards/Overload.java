package automaton.cards;

import automaton.FunctionHelper;
import automaton.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class Overload extends AbstractBronzeCard {

    public final static String ID = makeID("Overload");

    //stupid intellij stuff skill, self, common

    public Overload() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard q : FunctionHelper.held.group) {
            atb(new SFXAction("ORB_LIGHTNING_EVOKE"));
            addToBot(new VFXAction(new LightningEffect(q.hb.cX, q.hb.cY)));
            addToBot(new RepeatCardAction(q));
        }
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}