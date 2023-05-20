package collector.cards;

import collector.CollectorCollection;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Forgery extends AbstractCollectorCard {
    public final static String ID = makeID(Forgery.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 1, 1

    public Forgery() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!CollectorCollection.combatCollection.isEmpty()) {
            ArrayList<AbstractCard> options = new ArrayList<>();
            options.addAll(CollectorCollection.combatCollection.group);
            ArrayList<AbstractCard> toShow = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (options.isEmpty()) break;
                toShow.add(options.remove(AbstractDungeon.cardRandomRng.random(options.size() - 1)));
            }
            atb(new SelectCardsCenteredAction(toShow, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
                AbstractCard tar = cards.get(0);
                CollectorCollection.combatCollection.removeCard(tar);
                att(new MakeTempCardInHandAction(tar.makeStatEquivalentCopy(), 1 + magicNumber));
            }));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}