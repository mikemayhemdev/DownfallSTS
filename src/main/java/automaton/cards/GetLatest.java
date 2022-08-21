package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class GetLatest extends AbstractBronzeCard {

    public final static String ID = makeID("GetLatest");

    //stupid intellij stuff skill, self, uncommon

    public GetLatest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }
    public static AbstractCard getRandomEncode() {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL && c.rarity != CardRarity.BASIC) {
                eligibleCardsList.add(c.makeCopy());
            }
        }
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1));
        return qCardGet.makeCopy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard qCardGet = getRandomEncode();
        qCardGet.freeToPlayOnce = true;
        atb(new MakeTempCardInHandAction(qCardGet, true));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}