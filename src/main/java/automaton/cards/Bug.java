package automaton.cards;

import automaton.AutomatonMod;
import automaton.MechaHelper;
import automaton.actions.FireCardAction;
import automaton.actions.FireFromPileAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Bug extends AbstractBronzeCard {

    public final static String ID = makeID("Bug");

    //stupid intellij stuff skill, self, uncommon

    public Bug() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        AutoplayField.autoplay.set(this, true);
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        ArrayList<AbstractCard> allStoredCards = new ArrayList<AbstractCard>();
        allStoredCards.addAll(MechaHelper.blasters.group);
        allStoredCards.addAll(MechaHelper.shields.group);
        allStoredCards.addAll(MechaHelper.cores.group);
        if (!allStoredCards.isEmpty()) {
            AbstractCard q = allStoredCards.get(AbstractDungeon.cardRandomRng.random(0, allStoredCards.size()-1));
            if (q.hasTag(AutomatonMod.BLASTER)) {
                atb(new FireCardAction(q, MechaHelper.blasters));
            }
            else if (q.hasTag(AutomatonMod.SHIELD)) {
                atb(new FireCardAction(q, MechaHelper.shields));
            }
            else if (q.hasTag(AutomatonMod.CORE)) {
                atb(new FireCardAction(q, MechaHelper.cores));
            }
        }
        if (upgraded) {
            blck();
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}