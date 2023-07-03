package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class DarkApotheosis extends AbstractCollectorCard {
    public final static String ID = makeID(DarkApotheosis.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , , 

    public DarkApotheosis() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> toCheck = new ArrayList<>();
        toCheck.addAll(CollectorCollection.combatCollection.group);
        toCheck.addAll(Wiz.getAllCardsInCardGroups(true, false));
        toCheck.removeIf(q -> !CardModifierManager.hasModifier(q, CollectedCardMod.ID));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : toCheck) {
                    q.upgrade();
                }
            }
        });
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}