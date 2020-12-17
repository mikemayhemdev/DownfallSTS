package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class GetLatest extends AbstractBronzeCard {

    public final static String ID = makeID("GetLatest");

    //stupid intellij stuff skill, self, uncommon

    public GetLatest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING)) {
                eligibleCardsList.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING)) {
                eligibleCardsList.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING)) {
                eligibleCardsList.add(c);
            }
        }
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size()-1));
        qCardGet.setCostForTurn(0);
        atb(new MakeTempCardInHandAction(qCardGet, true));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}