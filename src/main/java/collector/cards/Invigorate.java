package collector.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import collector.powers.NextTurnVigorPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Invigorate extends AbstractCollectorCard {
    public final static String ID = makeID(Invigorate.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public Invigorate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        isPyre();
        MultiCardPreview.add(this, new Trip(), new Blind());
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new Trip());
        cards.add(new Blind());
        if (upgraded) for (AbstractCard c : cards) c.upgrade();
        atb(new SelectCardsCenteredAction(cards, 1, cardStrings.EXTENDED_DESCRIPTION[0], (selected) -> {
            makeInHandTop(selected.get(0));
        }));
    }

    public void upp() {
        AbstractCard q = new Trip();
        AbstractCard q2 = new Blind();
        q.upgrade();
        q2.upgrade();
        MultiCardPreview.clear(this);
        MultiCardPreview.add(this, q, q2);
    }
}